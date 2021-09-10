package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaobei.dto.AdminDto;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;
import com.kaobei.mapper.AdminMapper;
import com.kaobei.mapper.AdminRoleMapper;
import com.kaobei.service.AdminService;
import com.kaobei.utils.DtoEntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private AdminRoleMapper adminRoleMapper;


    @Override
    public void insertAdmin(AdminEntity adminEntity) {
        adminMapper.insert(adminEntity);
    }

    @Override
    public void insertAdminRole(AdminRoleEntity adminRoleEntity) {
        adminRoleMapper.insert(adminRoleEntity);
    }

    @Override
    public AdminEntity findAdminByUsername(String username) {
        QueryWrapper<AdminEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public List<AdminDto> findAreaAdminList(Long areaId) {
        QueryWrapper<AdminEntity> wrapper = new QueryWrapper<>();

        wrapper.eq("area_id",areaId);
        wrapper.eq("is_area_admin",1);
        List<AdminEntity> adminEntities = adminMapper.selectList(wrapper);

        List<AdminDto> adminDtos = DtoEntityUtils.parseToArray(adminEntities, AdminDto.class);

        return adminDtos;
    }

    @Override
    public List<AdminDto> findParkAdminList(Long parkId) {
        QueryWrapper<AdminEntity> wrapper = new QueryWrapper<>();

        wrapper.eq("park_id",parkId);
        wrapper.eq("is_park_admin",1);
        List<AdminEntity> adminEntities = adminMapper.selectList(wrapper);

        List<AdminDto> adminDtos = DtoEntityUtils.parseToArray(adminEntities, AdminDto.class);

        return adminDtos;
    }

    @Override
    public List<AdminRoleEntity> findAdminRolesByUsername(String username) {
        QueryWrapper<AdminRoleEntity>wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return adminRoleMapper.selectList(wrapper);
    }
}
