package com.kaobei.utils;


import com.kaobei.entity.ParkEntity;
import io.lettuce.core.RedisClient;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RedisGeoUtils {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    public static final String GEO_KEY = "geoKey";


    public boolean geoRmPark(Long parkId){
        RScoredSortedSet<Long> scoredSortedSet = redissonClient.getScoredSortedSet(GEO_KEY);

        return scoredSortedSet.remove(parkId);
    }


    public void geoAddPark(double lng, double lat, Long parkId){
        RGeo<Long> geo = redissonClient.getGeo(GEO_KEY);
        geo.add(lng, lat, parkId);
    }


    /*

    lng ,lat 经纬度
    半径 为radius
     */
    public List<Long> geoGetParkLIdListByNear(double lng, double lat, double radius,int count) {
        RGeo<Long> geo = redissonClient.getGeo(GEO_KEY);
        return geo.radius(lng, lat, radius, GeoUnit.METERS, GeoOrder.ASC,count);
    }


    public void flushRedisGeo(){
        redisTemplate.delete(GEO_KEY);
    }


}
