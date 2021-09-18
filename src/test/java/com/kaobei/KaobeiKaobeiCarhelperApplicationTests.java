package com.kaobei;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.commons.Pos;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.ComplaintEntity;
import com.kaobei.entity.DeviceEntity;
import com.kaobei.entity.UserEntity;
import com.kaobei.mapper.ParkMapper;
import com.kaobei.service.*;
import com.kaobei.utils.GeoUtils;
import com.kaobei.utils.RedisGeoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KaobeiKaobeiCarhelperApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private ParkService parkService;
    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private ParkRecordService parkRecordService;
    @Autowired
    private RedisGeoUtils  redisGeoUtils;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private GeoUtils geoUtils;

    @Autowired
    private ComplaintService complaintService;


    @Test
    void contextLoads() {
//        List<ParkDto> parkListByPosNear = parkService.findParkListByPosNear(119.00, 26.00, 1000.00, 2);
//
//        System.out.println(parkListByPosNear.toString());

//        List<ParkDto> dto = parkMapper.findParkDtoByKeyword("福大",new Page(1,2));
//        System.out.println(dto.toString());

//        Double areaRecordsCostSum = parkRecordService.getAreaRecordsCostSum(1L, );
//        System.out.println(areaRecordsCostSum.doubleValue());


//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date zero = calendar.getTime();
//        System.out.println(zero.toString());

//        Date zeroTime = DateUtils.getTomoZeroTime(new Date());
//        System.out.println(zeroTime.toString());
//
//        QueryWrapper<ParkEntity> wrapper = new QueryWrapper<>();
//        wrapper.eq("area_id",1);
//        wrapper.select("limit 1");
//        ParkEntity parkEntity = parkMapper.selectOne(wrapper);
//        System.out.println(parkEntity.toString());
//
//        List<Long> longList = redisGeoUtils.geoGetParkLIdListByNear(119.01, 26.01, 1000.00, 10);
//
//        System.out.println(longList.toString());

//        deviceService.insertDevice(new DeviceEntity(0L,1L,"w",00.00,00.00));
//        double distance = redisGeoUtils.getDistance(new Pos(119.19153, 26.05112), new Pos(119.19146, 26.05115));
//        System.out.println(distance);
//
//
//        Double distance1 = geoUtils.getDistance(GeoUtils.posParseToPoint(119.19146, 26.05115), GeoUtils.posParseToPoint(119.19153, 26.05112));
//        System.out.println(distance1.toString());
//        UserEntity userByOpenId = userService.findUserByOpenId("123456");
//        userByOpenId.setCarNumber("123123123132");
//        userService.updateUserByOpenId(userByOpenId);

        complaintService.insertComplaint(new ComplaintEntity(0L,"123456",0L,"123456",0));
    }

}
