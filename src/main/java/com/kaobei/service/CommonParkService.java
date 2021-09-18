package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.entity.CommonParkEntity;

public interface CommonParkService {

    CommonParkEntity insertCommonPark(CommonParkEntity commonParkEntity);

    CommonParkEntity findCommonParkById(Long commonId);

    CommonParkEntity findUserCommonPark(Long parkId,String openId);

    void upCommonParkTime(Long commonId);

    void updateCommonParkById(CommonParkEntity commonParkEntity);

    IPage findUserCommonPark(String openId,IPage page);
}
