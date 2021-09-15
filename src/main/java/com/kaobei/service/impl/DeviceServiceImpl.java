package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaobei.entity.DeviceEntity;
import com.kaobei.mapper.DeviceMapper;
import com.kaobei.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;


    @Override
    public DeviceEntity insertDevice(DeviceEntity deviceEntity) {
        deviceMapper.insert(deviceEntity);
        return deviceEntity;
    }

    @Override
    public List<DeviceEntity> findDevicesByParkId(Long parkId) {
        QueryWrapper<DeviceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("park_id",parkId);

        return deviceMapper.selectList(wrapper);
    }

    @Override
    public DeviceEntity findDeviceById(Long deviceId) {
        return deviceMapper.selectById(deviceId);
    }

    @Override
    public DeviceEntity findDeviceByWordArea(Long parkId, String deviceNumber) {
        QueryWrapper<DeviceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("park_id",parkId);
        wrapper.eq("device_number",deviceNumber);
        return deviceMapper.selectOne(wrapper);
    }
}
