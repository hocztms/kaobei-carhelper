package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.entity.ParkPlaceEntity;
import com.kaobei.mapper.PlaceMapper;
import com.kaobei.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    private PlaceMapper placeMapper;



    @Override
    public IPage getParkPlacePage(Long parkId, IPage iPage) {
        QueryWrapper<ParkPlaceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("park_id",parkId);

        return placeMapper.selectPage(iPage,wrapper);
    }


    @Override
    public ParkPlaceEntity insertParkPlace(ParkPlaceEntity parkPlaceEntity) {
        placeMapper.insert(parkPlaceEntity);
        return parkPlaceEntity;
    }

    @Override
    public void updateParkPlaceById(ParkPlaceEntity parkPlaceEntity) {
        int i = placeMapper.updateById(parkPlaceEntity);
        if (i==0){
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    public void deleteParkPlaceById(Long placeId) {
        placeMapper.deleteById(placeId);
    }

    @Override
    public ParkPlaceEntity userGrabParkPlaceByParkId(Long parkId) {
        QueryWrapper<ParkPlaceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("park_id",parkId);
        return placeMapper.selectPage(new Page<>(1, 1), wrapper).getRecords().get(0);
    }
}
