package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.entity.AreaEntity;

import java.util.List;

public interface AreaService {

    AreaEntity insertArea(AreaEntity areaEntity);

    void updateAreaById(AreaEntity areaEntity);

    void deleteAreaById(Long areaId);

    List<AreaEntity> findAreaPage(IPage iPage);
}
