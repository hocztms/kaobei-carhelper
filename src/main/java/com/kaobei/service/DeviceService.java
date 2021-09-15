package com.kaobei.service;

import com.kaobei.entity.DeviceEntity;

import java.util.List;

public interface DeviceService {
    DeviceEntity insertDevice(DeviceEntity deviceEntity);

    List<DeviceEntity> findDevicesByParkId(Long parkId);

    DeviceEntity findDeviceById(Long deviceId);

    DeviceEntity findDeviceByWordArea(Long parkId,String deviceNumber);
}
