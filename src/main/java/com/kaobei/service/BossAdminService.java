package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.commons.RestResult;
import com.kaobei.vo.AreaAdminVo;
import com.kaobei.vo.AreaVo;

public interface BossAdminService {

    RestResult bossCreateArea(AreaVo areaVo);

    RestResult bossCreateAreaAdmin(AreaAdminVo adminVo);

    RestResult bossGetAreaPage(IPage iPage);
}
