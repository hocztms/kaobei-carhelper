package com.kaobei.service;

import com.kaobei.dto.AdminDto;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;

import java.util.List;

public interface AdminService {

    void insertAdmin(AdminEntity adminEntity);

    void insertAdminRole(AdminRoleEntity adminRoleEntity);

    AdminEntity findAdminByUsername(String username);

    List<AdminDto> findAreaAdminList(Long areaId);

    List<AdminDto> findParkAdminList(Long parkId);

    List<AdminRoleEntity> findAdminRolesByUsername(String username);


}
