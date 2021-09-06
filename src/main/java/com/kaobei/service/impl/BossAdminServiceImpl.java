package com.kaobei.service.impl;

import com.kaobei.commons.RestResult;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AreaEntity;
import com.kaobei.service.AdminService;
import com.kaobei.service.AreaService;
import com.kaobei.service.BossAdminService;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.AreaAdminVo;
import com.kaobei.vo.AreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BossAdminServiceImpl implements BossAdminService {
    @Autowired
    private AreaService areaService;

    @Autowired
    private AdminService adminService;



    @Override
    public RestResult bossCreateArea(AreaVo areaVo) {
        try {
            AreaEntity areaEntity = new AreaEntity(areaVo.getAreaId(),areaVo.getAreaName());

            areaService.insertArea(areaEntity);

            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult bossCreateAreaAdmin(AreaAdminVo adminVo) {
        try {
            AdminEntity areaEntity = AdminEntity.builder()
                    .areaId(adminVo.getAreaId())
                    .username(adminVo.getUsername())
                    .password(adminVo.getPassword())
                    .status(1)
                    .build();

            adminService.insertAdmin(areaEntity);
            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult bossGetAreaPage(Long page, Long size) {
        try {

            return ResultUtils.success(areaService.findAreaPage(page,size));
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }
}
