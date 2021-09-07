package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.ParkEntity;
import com.kaobei.mapper.ParkMapper;
import com.kaobei.service.ParkService;
import com.kaobei.utils.DtoEntityUtils;
import com.kaobei.utils.RedisGeoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {
    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private RedisGeoUtils redisGeoUtils;


    @Override
    @CachePut(value = "park",key = "#result.parkId")
    public ParkEntity insertPark(ParkEntity parkEntity) {
        parkMapper.insert(parkEntity);
        return parkEntity;
    }

    @Override
    @Cacheable(value = "park",key = "#parkId")
    public ParkEntity findParkById(Long parkId) {
        return parkMapper.selectById(parkId);
    }

    @Override
    @CachePut(value = "park",key = "#parkEntity.parkId")
    public void updateParkById(ParkEntity parkEntity) {
        parkMapper.updateById(parkEntity);
    }

    @Override
    @CacheEvict(value = "park",key = "#parkId")
    public void deleteParkById(Long parkId) {
        parkMapper.deleteById(parkId);
    }



    @Override
    public List<ParkEntity> findAreaParkPage(Long areaId, Long page, Long size) {
        QueryWrapper<ParkEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("area_id",areaId);
        return parkMapper.selectPage(new Page<>(page,size),wrapper).getRecords();
    }

    @Override
    public List<ParkDto> findParkListByPosNear(Double lng, Double lat, Double radius, Integer count) {
        List<Long> list = redisGeoUtils.geoGetParkLIdListByNear(lng, lat, radius, count);

        List<ParkEntity> parkEntities = new ArrayList<>();
        for (Long parkId:list){
            ParkEntity parkById = findParkById(parkId);
            parkEntities.add(parkById);
        }
        List<ParkDto> parkDtos = DtoEntityUtils.parseToArray(parkEntities,ParkDto.class);
        return parkDtos;
    }

    @Override
    public List<ParkEntity> getInitializationParkList() {
        QueryWrapper<ParkEntity> wrapper = new QueryWrapper<>();

        wrapper.eq("status",1);

        return parkMapper.selectList(wrapper);
    }

    @Override
    public List<ParkDto> findParkByKeyword(String keyword, IPage iPage) {
        return parkMapper.findParkDtoByKeyword(keyword,iPage);
    }
}
