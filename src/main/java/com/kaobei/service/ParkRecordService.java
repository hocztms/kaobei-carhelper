package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.dto.ParkRecoedAbleDto;
import com.kaobei.dto.ParkRecordDto;
import com.kaobei.entity.ParkRecordEntity;

import java.util.List;

public interface ParkRecordService {

    ParkRecordEntity insertRecord(ParkRecordEntity parkRecordEntity);

    void updateRecord(ParkRecordEntity parkRecordEntity);

    void deleteRecord(Long parkId);


    /*
    获取用户未完成停车记录 如果没有允许停车 有则 获取到停车相关信息
     */
    ParkRecoedAbleDto getUserIsParkByOpenId(String openId);


    List<ParkRecordDto> getAreaRecordsByPage(Long areaId, IPage page);

    List<ParkRecordDto> getParkRecordsByPage(Long parkId,IPage page);


    Double getAreaRecordsCostSum(Long areaId);

}
