package com.kaobei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.commons.RestResult;
import com.kaobei.vo.ParkVo;
import com.kaobei.vo.PlaceVo;

import java.util.Date;

public interface ParkAdminService {

    RestResult parkAdminFreezePark(String username);

    RestResult parkAdminUnFreezePark(String username);

    RestResult parkAdminAddParkPlace(PlaceVo placeVo, String username);

    RestResult parkAdminGetParkPlacePage(IPage iPage, String username);

    RestResult parkAdminGetDateRecords(Date date,IPage iPage, String username);
}
