package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.entity.CommonParkEntity;
import com.kaobei.mapper.CommonParkMapper;
import com.kaobei.service.CommonParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonParkServiceImpl implements CommonParkService {
    @Autowired
    private CommonParkMapper commonParkMapper;

    @Override
    public CommonParkEntity insertCommonPark(CommonParkEntity commonParkEntity) {
        commonParkMapper.insert(commonParkEntity);
        return commonParkEntity;
    }

    @Override
    public CommonParkEntity findCommonParkById(Long commonId) {
        return commonParkMapper.selectById(commonId);
    }

    @Override
    public CommonParkEntity findUserCommonPark(Long parkId, String openId) {
        QueryWrapper<CommonParkEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("open_Id",openId);
        wrapper.eq("park_id",parkId);
        return commonParkMapper.selectOne(wrapper);
    }

    @Override
    public void upCommonParkTime(Long commonParkId) {
        CommonParkEntity commonParkEntity = findCommonParkById(commonParkId);
        commonParkEntity.setTimes(commonParkEntity.getTimes() + 1);
        updateCommonParkById(commonParkEntity);
    }

    @Override
    public void updateCommonParkById(CommonParkEntity commonParkEntity) {
        commonParkMapper.updateById(commonParkEntity);
    }

    @Override
    public IPage   findUserCommonPark(String openId, IPage page) {
        QueryWrapper<CommonParkEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        wrapper.orderByAsc("times");
        return commonParkMapper.selectPage(page,wrapper);
    }
}
