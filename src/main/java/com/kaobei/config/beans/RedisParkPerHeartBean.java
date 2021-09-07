package com.kaobei.config.beans;

import com.kaobei.entity.ParkEntity;
import com.kaobei.service.ParkService;
import com.kaobei.utils.RedisGeoUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RedisParkPerHeartBean implements InitializingBean {

    @Autowired
    private ParkService parkService;
    @Autowired
    private RedisGeoUtils redisGeoUtils;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<ParkEntity> initializationParkList = parkService.getInitializationParkList();

        for (ParkEntity parkEntity:initializationParkList){
            redisGeoUtils.geoAddPark(parkEntity.getLng(),parkEntity.getLat(),parkEntity.getParkId());
        }

    }
}
