package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.entity.AreaEntity;
import com.kaobei.mapper.AreaMapper;
import com.kaobei.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public AreaEntity insertArea(AreaEntity areaEntity) {
        areaMapper.insert(areaEntity);
        return areaEntity;
    }

    @Override
    public void updateAreaById(AreaEntity areaEntity) {
        areaMapper.updateById(areaEntity);
    }

    @Override
    public void deleteAreaById(Long areaId) {
        areaMapper.deleteById(areaId);
    }

    @Override
    public List<AreaEntity> findAreaPage(Long page, Long size) {
        QueryWrapper<AreaEntity> wrapper = new QueryWrapper<>();
        return areaMapper.selectPage(new Page<>(page,size),wrapper).getRecords();
    }
}
