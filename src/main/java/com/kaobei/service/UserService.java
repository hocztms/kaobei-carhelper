package com.kaobei.service;

import com.kaobei.commons.RestResult;
import com.kaobei.entity.UserEntity;
import com.kaobei.entity.UserRoleEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void insertUser(UserEntity userEntity);

    UserEntity findUserByOpenId(String openId);

    List<UserRoleEntity> findUserRolesByOpenId(String openId);

    RestResult getPlateByPicture(String fileName);

    RestResult doneLoad(MultipartFile file);
}
