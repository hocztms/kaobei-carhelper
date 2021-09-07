package com.kaobei.service.impl;

import com.kaobei.commons.RestResult;
import com.kaobei.commons.Role;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;
import com.kaobei.entity.ParkEntity;
import com.kaobei.service.AdminService;
import com.kaobei.service.AreaAdminService;
import com.kaobei.service.ParkService;
import com.kaobei.utils.RedisGeoUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.ParkAdminVo;
import com.kaobei.vo.ParkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaAdminServiceImpl implements AreaAdminService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ParkService parkService;
    @Autowired
    private RedisGeoUtils redisGeoUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public RestResult areaAdminCreatePark(ParkVo parkVo, String username) {
        try {


            AdminEntity adminByUsername = adminService.findAdminByUsername(username);

            ParkEntity parkEntity = ParkEntity.builder()
                    .parkId(parkVo.getParkId())
                    .areaId(adminByUsername.getAreaId())
                    .parkName(parkVo.getParkName())
                    .lat(parkVo.getLat())
                    .lng(parkVo.getLng())
                    .parkPlaceSum(0)
                    .placeNum(0)
                    .status(1)
                    .version(0)
                    .build();

            ParkEntity park = parkService.insertPark(parkEntity);

            redisGeoUtils.geoAddPark(park.getLng(),park.getLat(),park.getParkId());
            return ResultUtils.success(park.getParkId());
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult areaAdminGetAreaParkPage(Long page, Long size, String username) {
        try {
            AdminEntity adminByUsername = adminService.findAdminByUsername(username);

            List<ParkEntity> areaParkPage = parkService.findAreaParkPage(adminByUsername.getAreaId(), page, size);

            return ResultUtils.success(areaParkPage);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult areaAdminCreateParkAdmin(ParkAdminVo parkAdminVo, String username) {
        try {
            AdminEntity adminEntity = adminService.findAdminByUsername(username);
            AdminEntity admin = AdminEntity.builder()
                    .username(parkAdminVo.getUsername())
                    .password(passwordEncoder.encode(parkAdminVo.getPassword()))
                    .areaId(adminEntity.getAreaId())
                    .parkId(parkAdminVo.getParkId())
                    .status(1)
                    .build();

            adminService.insertAdmin(adminEntity);

            adminService.insertAdminRole(new AdminRoleEntity(admin.getUsername(), Role.PARK_ADMIN));

            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }
}
