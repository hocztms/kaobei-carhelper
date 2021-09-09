package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaobei.commons.RestResult;
import com.kaobei.entity.UserEntity;
import com.kaobei.entity.UserRoleEntity;
import com.kaobei.mapper.UserMapper;
import com.kaobei.mapper.UserRoleMapper;
import com.kaobei.service.UserService;
import com.kaobei.util.BaiduUtil.AuthService;
import com.kaobei.util.BaiduUtil.Base64Util;
import com.kaobei.util.BaiduUtil.FileUtil;
import com.kaobei.util.HttpUtil;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.DownLodeVo;
import com.kaobei.vo.GetPlateVo;
import com.kaobei.vo.SetPlateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;


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
        wrapper.eq("open_id", userEntity);
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
