package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.commons.RestResult;
import com.kaobei.vo.ParkAdminVo;
import com.kaobei.vo.ParkVo;

public interface AreaAdminService {

    RestResult areaAdminCreatePark(ParkVo parkVo,String username);

    RestResult areaAdminGetAreaParkPage(Long page,Long size,String username);

    RestResult areaAdminCreateParkAdmin(ParkAdminVo parkAdminVo,String username);

    RestResult areaAdminGetAreaParkPage(String username, IPage iPage);
}
