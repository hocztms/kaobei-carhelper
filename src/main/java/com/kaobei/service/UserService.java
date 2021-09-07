package com.kaobei.service;

import com.kaobei.commons.RestResult;
import com.kaobei.entity.UserEntity;
import com.kaobei.entity.UserRoleEntity;
import com.kaobei.vo.DownLodeVo;
import com.kaobei.vo.GetPlateVo;
import com.kaobei.vo.SetPlateVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void insertUser(UserEntity userEntity);

    UserEntity findUserByOpenId(String openId);

    List<UserRoleEntity> findUserRolesByOpenId(String openId);

    RestResult getPlateByPicture(GetPlateVo getPlateVo);

    RestResult doneLoad(DownLodeVo downLodeVo);

    void setInitRole(UserRoleEntity user);

    RestResult setPlate(SetPlateVo setPlateVo,String openId);
}
