package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.entity.AreaEntity;

public interface AreaService {

    AreaEntity insertArea(AreaEntity areaEntity);

    void updateAreaById(AreaEntity areaEntity);

    void deleteAreaById(Long areaId);

    IPage  findAreaPage(IPage iPage);
}
