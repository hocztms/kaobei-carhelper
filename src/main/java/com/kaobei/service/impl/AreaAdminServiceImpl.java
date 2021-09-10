package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kaobei.commons.RestResult;
import com.kaobei.commons.Role;
import com.kaobei.dto.AdminDto;
import com.kaobei.dto.ParkAmountDto;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;
import com.kaobei.entity.ParkEntity;
import com.kaobei.service.AdminService;
import com.kaobei.service.AreaAdminService;
import com.kaobei.service.ParkRecordService;
import com.kaobei.service.ParkService;
import com.kaobei.utils.DtoEntityUtils;
import com.kaobei.utils.RedisGeoUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.ParkAdminVo;
import com.kaobei.vo.ParkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private ParkRecordService parkRecordService;


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
    public RestResult areaAdminCreateParkAdmin(ParkAdminVo parkAdminVo, String username) {
        try {
            AdminEntity adminEntity = adminService.findAdminByUsername(username);
            AdminEntity admin = AdminEntity.builder()
                    .username(parkAdminVo.getUsername())
                    .password(passwordEncoder.encode(parkAdminVo.getPassword()))
                    .areaId(adminEntity.getAreaId())
                    .parkId(parkAdminVo.getParkId())
                    .isParkAdmin(1)
                    .isAreaAdmin(0)
                    .status(1)
                    .build();

            adminService.insertAdmin(admin);

            adminService.insertAdminRole(new AdminRoleEntity(admin.getUsername(), Role.PARK_ADMIN));

            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult areaAdminGetAreaParkPage(String username, IPage iPage) {
        try {
            AdminEntity adminEntity = adminService.findAdminByUsername(username);

            IPage areaParkPage = parkService.findAreaParkPage(adminEntity.getAreaId(), iPage);
            RestResult restResult = ResultUtils.success();
            restResult.putData(DtoEntityUtils.parseToArray(areaParkPage.getRecords(), ParkDto.class));
            restResult.putTotal(areaParkPage.getTotal());
            return restResult;
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult areaAdminGetParkAdminList(Long parkId, String username) {
        try {
            ParkEntity parkById = parkService.findParkById(parkId);
            AdminEntity adminEntity = adminService.findAdminByUsername(username);

            if (adminEntity.getAreaId().intValue()!=parkById.getAreaId().intValue()){
                return ResultUtils.error("无权限");
            }

            List<AdminDto> parkAdminList = adminService.findParkAdminList(parkId);
            return ResultUtils.success(parkAdminList);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult areaAdminGetParkDaysAmount(List<Date> dates, Long parkId, String username) {
        try {
            ParkEntity parkEntity = parkService.findParkById(parkId);
            AdminEntity adminEntity = adminService.findAdminByUsername(username);

            if (parkEntity.getAreaId().intValue()!=adminEntity.getAreaId().intValue()){
                return ResultUtils.error("无权限");
            }


            List<ParkAmountDto> parkAmountDtos = new ArrayList<>();

            for (Date date:dates){
                Double parkRecordsCostSum = parkRecordService.getParkRecordsCostSum(parkId, date);
                parkAmountDtos.add(new ParkAmountDto(parkId,parkRecordsCostSum,date));
            }



            return ResultUtils.success(parkAmountDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }
}
