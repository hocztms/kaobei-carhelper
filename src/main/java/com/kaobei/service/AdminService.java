package com.kaobei.service;

import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;

import java.util.List;

public interface AdminService {


    AdminEntity findAdminByUsername(String username);

    List<AdminRoleEntity> findAdminRolesByUsername(String username);
}
