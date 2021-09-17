package com.kaobei.service;

import com.kaobei.commons.RestResult;
import com.kaobei.entity.UserEntity;
import com.kaobei.entity.UserRoleEntity;
import com.kaobei.vo.DeviceVo;
import com.kaobei.vo.GetPlateVo;
import com.kaobei.vo.SetPlateVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void insertUser(UserEntity userEntity);

    UserEntity findUserByOpenId(String openId);

    void updateUserByOpenId(UserEntity userEntity);

    List<UserRoleEntity> findUserRolesByOpenId(String openId);

    RestResult getPlateByPicture(GetPlateVo getPlateVo);

    RestResult doneLoad(MultipartFile file);

    void setInitRole(UserRoleEntity user);

    RestResult setPlate(SetPlateVo setPlateVo, String openId);

    String getPlate(String account);

    /*

    用户抢车位方法
     */
    RestResult userGrabParkPlace(Long parkId, String openId);

    RestResult userCancelPark(String openId);


    RestResult userGetParkPlaceDis(DeviceVo deviceVo,String openId);

}
