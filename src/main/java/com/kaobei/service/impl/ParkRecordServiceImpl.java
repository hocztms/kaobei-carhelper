package com.kaobei.service.impl;

import com.kaobei.entity.ParkRecordEntity;
import com.kaobei.mapper.ParkRecordMapper;
import com.kaobei.service.ParkRecordService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkRecordServiceImpl implements ParkRecordService {
    @Autowired
    private ParkRecordMapper parkRecordMapper;


    @Override
    public ParkRecordEntity insertParkRecord(ParkRecordEntity parkRecordEntity) {
        parkRecordMapper.insert(parkRecordEntity);
        return parkRecordEntity;
    }
}
