package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.entity.ParkPlaceEntity;

public interface PlaceService {


    ParkPlaceEntity insertParkPlace(ParkPlaceEntity parkPlaceEntity);

    void updateParkPlaceById(ParkPlaceEntity parkPlaceEntity);

    void deleteParkPlaceById(Long placeId);

    ParkPlaceEntity userGrabParkPlaceByParkId(Long parkId);

    IPage getParkPlacePage(Long parkId,IPage iPage);
}
