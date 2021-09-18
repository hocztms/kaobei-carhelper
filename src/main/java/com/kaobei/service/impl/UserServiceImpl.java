package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.kaobei.commons.Circle;
import com.kaobei.commons.Point;
import com.kaobei.commons.RestResult;
import com.kaobei.dto.AdminDto;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.UserEntity;
import com.kaobei.entity.UserRoleEntity;
import com.kaobei.dto.PlaceDto;
import com.kaobei.entity.*;
import com.kaobei.mapper.UserMapper;
import com.kaobei.mapper.UserRoleMapper;
import com.kaobei.service.*;
import com.kaobei.util.BaiduUtil.AuthService;
import com.kaobei.util.BaiduUtil.Base64Util;
import com.kaobei.util.BaiduUtil.FileUtil;
import com.kaobei.util.HttpUtil;
import com.kaobei.utils.DtoEntityUtils;
import com.kaobei.utils.GeoUtils;
import com.kaobei.utils.RedisGeoUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.*;
import com.kaobei.vo.SetPlateVo;
import com.kaobei.webSocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private ParkRecordService parkRecordService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private PlaceService placeService;
    @Resource
    private ParkService parkService;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private DeviceService deviceService;
    @Resource
    private RedisGeoUtils redisGeoUtils;
    @Resource
    private GeoUtils geoUtils;
    @Resource
    private ComplaintService complaintService;
    @Resource
    private AdminService adminService;
    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    private CommonParkService commonParkService;


    @Override
    public void insertUser(UserEntity userEntity) {
        userMapper.insert(userEntity);
    }

    @Override
    public UserEntity findUserByOpenId(String openId) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);

        return userMapper.selectOne(wrapper);
    }

    @Override
    public void updateUserByOpenId(UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", userEntity.getOpenId());
        userMapper.update(userEntity,wrapper);
    }

    @Override
    public List<UserRoleEntity> findUserRolesByOpenId(String openId) {
        QueryWrapper<UserRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        return userRoleMapper.selectList(wrapper);
    }

    public RestResult doneLoad(MultipartFile file) {
        if (file.isEmpty()){
            return ResultUtils.systemError();
        }
        String fileName=null;
        String name=file.getOriginalFilename();
        String[] a = name.split("\\.");
        String type=a[a.length-1];
        RestResult result = null;
        if (type.equals("png")||type.equals("jpg")){
            fileName = String.valueOf(UUID.randomUUID())+"."+type;
            String path="C:\\upload";
            InputStream inputStream = null;
            File files = null;
            try {
                files = File.createTempFile("temp", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                file.transferTo(files);   //sourceFile为传入的MultipartFile
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = new FileInputStream(files);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            files.deleteOnExit();
            OutputStream os = null;
            try {
                byte[] bs = new byte[1024];
                int len=1024;
                File tempFile = new File(path);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
                while (true) {
                    len = inputStream.read(bs) ;
                    if (len==-1){
                        break;
                    }
                    os.write(bs, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result =ResultUtils.success(fileName);
        }
        return result;
    }

    @Override
    public void setInitRole(UserRoleEntity user) {
        userRoleMapper.insert(user);
    }

    @Override
    public RestResult setPlate(SetPlateVo setPlateVo, String openId) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        UserEntity userEntity=userMapper.selectOne(wrapper);
        userEntity.setCarNumber(setPlateVo.getPlate());
        userMapper.update(userEntity,wrapper);
        return new RestResult(1,"绑定成功",null);
    }

    @Override
    public RestResult userGrabParkPlace(Long parkId, String openId) {
        try {
            UserEntity userEntity = findUserByOpenId(openId);

            if (userEntity.getAmount()<=0){
                return ResultUtils.error("当前账户余额不足");
            }

            ParkEntity parkEntity = parkService.findParkById(parkId);

            if (parkRecordService.getUserIsParkByOpenId(openId)!=null){
                return ResultUtils.error("请勿重复停车");
            }


            if (parkEntity.getPlaceNum()==0){
                return ResultUtils.error("停车场已满");
            }


            //redis 分布式事务锁
            RLock rLock = redissonClient.getFairLock(parkId.toString());

            //设置基础值为非
            boolean succeed = false;
            try {

                //等待50s
                succeed = rLock.tryLock(50,20, TimeUnit.SECONDS);
            }catch (Exception e){
                e.printStackTrace();
                return ResultUtils.error("服务繁忙请重试");
            }

            if (succeed){
                log.info("用户:{}   正在抢车位",openId);
                if (parkEntity.getPlaceNum()==0){
                    return ResultUtils.error("停车场已满");
                }

                try {
                    //抢车位
                    ParkPlaceEntity parkPlaceEntity = placeService.userGrabParkPlaceByParkId(parkId);
                    parkPlaceEntity.setIsOccupied(1);
                    placeService.updateParkPlaceById(parkPlaceEntity);
                    //创建记录
                    ParkRecordEntity parkRecordEntity = parkRecordService.insertRecord(new ParkRecordEntity(0L, parkEntity.getAreaId(), parkId, parkPlaceEntity.getPlaceId(), userEntity.getOpenId(), null, null, null, 0, 0));

                    //修改外部可停数量
                    parkEntity.setPlaceNum(parkEntity.getPlaceNum() -1);
                    parkService.updateParkById(parkEntity);


                    redisTemplate.opsForValue().set("delay::"+parkRecordEntity.getRecordId(),parkRecordEntity,15,TimeUnit.MINUTES);


                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Long recordId = parkRecordEntity.getRecordId();

                            try {
                                Thread.sleep(5*1000*1);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            ParkRecordEntity record = (ParkRecordEntity)redisTemplate.opsForValue().get("delay::" + recordId);

                            if (record==null) {
                                return;
                            }

                            log.info("用户 :{} 未停车 取消资格",record.getOpenId() );
                            ParkEntity parkById = parkService.findParkById(record.getParkId());
                            parkById.setPlaceNum(parkById.getPlaceNum() + 1);
                            parkService.updateParkById(parkEntity);


                            parkRecordService.deleteRecord(recordId);

                            ParkPlaceEntity place = placeService.findPlaceById(record.getPlaceId());
                            place.setIsOccupied(0);

                            placeService.updateParkPlaceById(place);
                        }
                    });
                    thread.start();
                    return ResultUtils.success(DtoEntityUtils.parseToObject(parkPlaceEntity, PlaceDto.class));
                }catch (Exception e){
                    e.printStackTrace();
                    return ResultUtils.error("服务繁忙");
                }finally {
                    rLock.unlock();
                }
            }

            return ResultUtils.error("服务器繁忙");
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult userCancelPark(String openId) {
        try {
            ParkRecordEntity record = parkRecordService.getUserIsParkByOpenId(openId);
            if (record ==null){
                return ResultUtils.error("请勿重复操作");
            }

            redisTemplate.delete("delay::" + record.getRecordId());

            ParkEntity parkById = parkService.findParkById(record.getParkId());
            parkById.setPlaceNum(parkById.getPlaceNum() + 1);
            parkService.updateParkById(parkById);

            ParkPlaceEntity placeById = placeService.findPlaceById(record.getPlaceId());
            placeById.setIsOccupied(0);
            placeService.updateParkPlaceById(placeById);

            parkRecordService.deleteRecord(record.getRecordId());


            return ResultUtils.success();
        }catch (Exception e){

            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult userGetParkPlaceDis(DeviceVo deviceVo, String openId) {
        try {
            ParkRecordEntity record = parkRecordService.getUserIsParkByOpenId(openId);


            if (record==null){
                return ResultUtils.error("错误操作");
            }


            ParkPlaceEntity placeById = placeService.findPlaceById(record.getPlaceId());


            Point point = GeoUtils.posParseToPoint(placeById.getLng(),placeById.getLat());


            List<Circle> circles = new ArrayList<>();
            List<DeviceVo.Device> list = deviceVo.getList();
            for (DeviceVo.Device device:list){
                DeviceEntity parkDevice = deviceService.findDeviceByWordArea(record.getParkId(), device.getDeviceNumber());
                if (parkDevice==null){
                    return ResultUtils.error("设备不存在");
                }
                circles.add(new Circle(GeoUtils.posParseToPoint(parkDevice.getLng(),parkDevice.getLat()),device.getDistance()));
            }

            if (circles.size()!=3){
                return ResultUtils.error("格式出错");
            }

            Point realTimePoint = geoUtils.getRealTimePoint(circles.get(0), circles.get(1), circles.get(2));

            Double distance =  geoUtils.getDistance(realTimePoint, point);


            return ResultUtils.success(Math.sqrt(distance));
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult userParking(DeviceVo deviceVo, String openId) {
        try {
            ParkRecordEntity record = parkRecordService.getUserIsParkByOpenId(openId);


            if (record==null){
                return ResultUtils.error("错误操作");
            }

            if (record.getStartTime()!=null){
                return ResultUtils.error("请勿重复操作");
            }


            ParkPlaceEntity placeById = placeService.findPlaceById(record.getPlaceId());


            Point point = GeoUtils.posParseToPoint(placeById.getLng(),placeById.getLat());


            List<Circle> circles = new ArrayList<>();
            List<DeviceVo.Device> list = deviceVo.getList();
            for (DeviceVo.Device device:list){
                DeviceEntity parkDevice = deviceService.findDeviceByWordArea(record.getParkId(), device.getDeviceNumber());
                if (parkDevice==null){
                    return ResultUtils.error("设备不存在");
                }
                circles.add(new Circle(GeoUtils.posParseToPoint(parkDevice.getLng(),parkDevice.getLat()),device.getDistance()));
            }

            if (circles.size()!=3){
                return ResultUtils.error("格式出错");
            }

            Point realTimePoint = geoUtils.getRealTimePoint(circles.get(0), circles.get(1), circles.get(2));

            Double distance =  geoUtils.getDistance(realTimePoint, point);

            if (Math.sqrt(distance)>10.00){

                return ResultUtils.error("请到指定地点停车");
            }

            record.setStartTime(new Date());

            parkRecordService.updateRecord(record);

            redisTemplate.delete("delay::" + record.getRecordId());
            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult userEndPark(String openId) {
        try {
            ParkRecordEntity record = parkRecordService.getUserIsParkByOpenId(openId);
            if (record==null||record.getStartTime() ==null){
                return ResultUtils.error("错误操作");
            }
            if (record.getEndTime()!=null){
                return ResultUtils.error("请勿重复操作");
            }


            record.setEndTime(new Date());

            long time = record.getEndTime().getTime() - record.getStartTime().getTime();

            long remainder = time%(1000 * 60 * 60);

            long hour = time / (1000 * 60 * 60);

            if (remainder!=0){
                hour++;
            }

            ParkEntity parkEntity = parkService.findParkById(record.getParkId());
            double cost = hour * parkEntity.getCharge();
            record.setCost(cost);
            record.setStatus(1);
            parkRecordService.updateRecord(record);

            UserEntity userEntity = findUserByOpenId(record.getOpenId());
            double amount = userEntity.getAmount() - cost;
            userEntity.setAmount(amount);

            updateUserByOpenId(userEntity);

            parkEntity.setPlaceNum(parkEntity.getPlaceNum() + 1);
            parkService.updateParkById(parkEntity);


            String msg = "您的停车已经完成 共计:" + cost + "元";

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    CommonParkEntity userCommonPark = commonParkService.findUserCommonPark(parkEntity.getParkId(), openId);
                    if (userCommonPark==null){
                        commonParkService.insertCommonPark(new CommonParkEntity(0L,openId,parkEntity.getParkId(),parkEntity.getParkName(),1L));
                    }
                    else {
                        commonParkService.upCommonParkTime(userCommonPark.getCommonId());
                    }
                }
            });

            thread.start();
            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult userFeedBack(ComplaintVo complaintVo, String openId) {
        try {

            ComplaintEntity complaint = ComplaintEntity.builder()
                    .complaintId(0L)
                    .content(complaintVo.getContent())
                    .openId(openId)
                    .status(0)
                    .parkId(complaintVo.getParkId())
                    .build();

            complaintService.insertComplaint(complaint);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<AdminDto> adminEntities = adminService.findParkAdminList(complaint.getParkId());
                    for (AdminDto adminDto:adminEntities){
                        webSocketServer.sendInfo(adminDto.getUsername(),new SocketMessage(1,"您有新的反馈消息待处理...").toString());
                    }
                }
            });

            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult userGetCommonPark(String openId, IPage iPage) {
        try {
            IPage userCommonPark = commonParkService.findUserCommonPark(openId, iPage);

            List<CommonParkEntity> commonParkEntities = userCommonPark.getRecords();
            List<ParkEntity> parkEntities = new ArrayList<>();
            for (CommonParkEntity commonParkEntity:commonParkEntities){
                ParkEntity parkById = parkService.findParkById(commonParkEntity.getParkId());
                parkEntities.add(parkById);
            }
            RestResult restResult = ResultUtils.success();
            restResult.putTotal(userCommonPark.getTotal());
            restResult.putData(DtoEntityUtils.parseToArray(parkEntities, ParkDto.class));

            return restResult;
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public String getPlate(String account){
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", account);
        return userMapper.selectOne(wrapper).getCarNumber();
    }

    public RestResult getPlateByPicture(GetPlateVo getPlateVo) {
        String fileName=getPlateVo.getFileName();
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        try {
            // 本地文件路径
            String filePath = "C:\\upload\\"+fileName;//[本地文件路径]
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();//[调用鉴权接口获取的token]
            return ResultUtils.success(HttpUtil.post(url, accessToken, param));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtils.systemError();
    }
}
