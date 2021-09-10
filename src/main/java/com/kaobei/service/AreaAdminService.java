package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.commons.RestResult;
import com.kaobei.vo.ParkAdminVo;
import com.kaobei.vo.ParkVo;

import java.util.Date;
import java.util.List;

public interface AreaAdminService {

    RestResult areaAdminCreatePark(ParkVo parkVo,String username);


    RestResult areaAdminCreateParkAdmin(ParkAdminVo parkAdminVo,String username);

    RestResult areaAdminGetAreaParkPage(String username, IPage iPage);

    RestResult areaAdminGetParkAdminList(Long parkId,String username);


    RestResult areaAdminGetParkDaysAmount(List<Date> dates, Long parkId,String username);
}
