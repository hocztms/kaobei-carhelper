package com.kaobei.service;

import com.kaobei.entity.UserEntity;
import com.kaobei.entity.UserRoleEntity;

import java.util.List;

public interface UserService {

    void insertUser(UserEntity userEntity);

    UserEntity findUserByOpenId(String openId);

    List<UserRoleEntity> findUserRolesByOpenId(String openId);

}
