package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.ParkEntity;

import java.util.List;

public interface ParkService {

    ParkEntity insertPark(ParkEntity parkEntity);

    ParkEntity findParkById(Long parkId);


    void updateParkById(ParkEntity parkEntity);


    void deleteParkById(Long parkId);

    List<ParkEntity> findAreaParkPage(Long areaId,Long page,Long size);




    /*
    获取 最近距离

    radius 某一半径内
    count 元素个数
     */
    List<ParkDto> findParkListByPosNear(Double lng, Double Lat, Double radius, Integer count);


    /*
    关键词搜索
     */
    List<ParkDto> findParkByKeyword(String keyword, IPage iPage);

    List<ParkEntity> getInitializationParkList();
}
