package com.kaobei.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.kaobei.commons.RestResult;
import com.kaobei.dto.ParkRecordDto;
import com.kaobei.dto.PlaceDto;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.ParkEntity;
import com.kaobei.entity.ParkPlaceEntity;
import com.kaobei.service.*;
import com.kaobei.utils.DateUtils;
import com.kaobei.utils.DtoEntityUtils;
import com.kaobei.utils.RedisGeoUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.PlaceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ParkAdminServiceImpl implements ParkAdminService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ParkService parkService;
    @Autowired
    private RedisGeoUtils redisGeoUtils;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private ParkRecordService parkRecordService;


    @Override
    public RestResult parkAdminFreezePark(String username) {
        try {
            AdminEntity adminByUsername = adminService.findAdminByUsername(username);

            ParkEntity parkEntity = parkService.findParkById(adminByUsername.getParkId());

            if (parkEntity.getStatus()==0){
                return ResultUtils.error("请勿重复操作");
            }


            parkEntity.setStatus(0);

            parkService.updateParkById(parkEntity);

            redisGeoUtils.geoRmPark(parkEntity.getParkId());
            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult parkAdminUnFreezePark(String username) {
        try {
            AdminEntity adminByUsername = adminService.findAdminByUsername(username);
            System.out.println(adminByUsername.toString());
            ParkEntity parkEntity = parkService.findParkById(adminByUsername.getParkId());

            if (parkEntity==null){
                System.out.println("dsdsd");
                return ResultUtils.systemError();
            }
            if (parkEntity.getStatus().intValue()==1){
                return ResultUtils.error("请勿重复操作");
            }


            parkEntity.setStatus(1);

            parkService.updateParkById(parkEntity);

            redisGeoUtils.geoAddPark(parkEntity.getLng(),parkEntity.getLat(),parkEntity.getParkId());
            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult parkAdminAddParkPlace(PlaceVo placeVo, String username) {
        try {
            AdminEntity adminEntity = adminService.findAdminByUsername(username);
            ParkEntity parkEntity = parkService.findParkById(adminEntity.getParkId());
            ParkPlaceEntity  placeEntity = ParkPlaceEntity.builder()
                    .placeId(0L)
                    .parkId(parkEntity.getParkId())
                    .placeNumber(placeVo.getPlaceNumber())
                    .lat(placeVo.getLat())
                    .lng(placeVo.getLng())
                    .isOccupied(0)
                    .version(0)
                    .build();


            placeService.insertParkPlace(placeEntity);

            parkEntity.setPlaceNum(parkEntity.getPlaceNum() + 1);
            parkEntity.setParkPlaceSum(parkEntity.getParkPlaceSum() +1);

            parkService.updateParkById(parkEntity);
            return ResultUtils.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult parkAdminGetParkPlacePage(IPage iPage, String username) {
        try {
            AdminEntity adminByUsername = adminService.findAdminByUsername(username);

            IPage parkPlacePage = placeService.getParkPlacePage(adminByUsername.getParkId(), iPage);

            RestResult restResult = ResultUtils.success();
            restResult.putData(DtoEntityUtils.parseToArray(parkPlacePage.getRecords(), PlaceDto.class));
            restResult.putTotal(parkPlacePage.getTotal());

            return restResult;
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }

    @Override
    public RestResult parkAdminGetDateRecords(Date date,IPage iPage, String username) {
        try {
            AdminEntity adminEntity = adminService.findAdminByUsername(username);

            IPage page = parkRecordService.getParkDateRecordsByPage(adminEntity.getParkId(), DateUtils.getZeroTime(date), iPage);

            RestResult restResult = ResultUtils.success();
            restResult.putData(DtoEntityUtils.parseToArray(page.getRecords(), ParkRecordDto.class));
            restResult.putTotal(page.getTotal());
            return restResult;
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.systemError();
        }
    }
}
