package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.commons.RestResult;
import com.kaobei.commons.Role;
import com.kaobei.dto.ParkRecordDto;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;
import com.kaobei.entity.AreaEntity;
import com.kaobei.service.AdminService;
import com.kaobei.service.AreaService;
import com.kaobei.service.BossAdminService;
import com.kaobei.service.ParkRecordService;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.AreaAdminVo;
import com.kaobei.vo.AreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BossAdminServiceImpl implements BossAdminService {
    @Autowired
    private AreaService areaService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ParkRecordService parkRecordService;



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
            AdminEntity adminEntity = AdminEntity.builder()
                    .areaId(adminVo.getAreaId())
                    .username(adminVo.getUsername())
                    .password(passwordEncoder.encode(adminVo.getPassword()))
                    .status(1)
                    .parkId(null)
                    .build();

            adminService.insertAdmin(adminEntity);
            adminService.insertAdminRole(new AdminRoleEntity(adminEntity.getUsername(), Role.AREA_ADMIN));
            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult bossGetAreaPage(IPage iPage) {
        try {
            return ResultUtils.success(areaService.findAreaPage(iPage));
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult bossGetAreaRecordPage(Long areaId, IPage iPage) {
        try {
            Double sum = parkRecordService.getAreaRecordsCostSum(areaId);
            List<ParkRecordDto> areaRecordsByPage = parkRecordService.getAreaRecordsByPage(areaId, iPage);
            RestResult restResult = new RestResult();
            restResult.put("sum",sum);
            restResult.put("records",areaRecordsByPage);

            return restResult;
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }
}
