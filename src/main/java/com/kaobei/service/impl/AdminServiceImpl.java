package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;
import com.kaobei.mapper.AdminMapper;
import com.kaobei.mapper.AdminRoleMapper;
import com.kaobei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;


    @Override
    public void insertAdmin(AdminEntity adminEntity) {
        adminMapper.insert(adminEntity);
    }

    @Override
    public AdminEntity findAdminByUsername(String username) {
        QueryWrapper<AdminEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public List<AdminRoleEntity> findAdminRolesByUsername(String username) {
        QueryWrapper<AdminRoleEntity>wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return adminRoleMapper.selectList(wrapper);
    }
}
