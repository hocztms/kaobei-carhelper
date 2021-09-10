package com.kaobei.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.commons.RestResult;
import com.kaobei.service.AreaAdminService;
import com.kaobei.utils.JwtTokenUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.ParkAdminVo;
import com.kaobei.vo.ParkVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/area")
@PreAuthorize("hasAuthority('areaAdmin')")
@RestController
@Slf4j
public class AreaAdminController {

    @Autowired
    private AreaAdminService adminService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;


    @PostMapping("/createPark")
    public RestResult areaAdminCreatePark(@Validated  @RequestBody ParkVo parkVo, HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return adminService.areaAdminCreatePark(parkVo,account);
    }

    @PostMapping("/createParkAdmin")
    public RestResult areaAdminCreateParkAdmin(@Validated @RequestBody ParkAdminVo parkAdminVo,HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return adminService.areaAdminCreateParkAdmin(parkAdminVo,account);
    }

    @GetMapping("/getParkAdmin")
    public RestResult getParkAdmin(long parkId,HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return adminService.areaAdminGetParkAdminList(parkId,account);
    }


    @GetMapping("/getAreaParkPage")
    public RestResult getAreaParkPage(long page,long size,HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return adminService.areaAdminGetAreaParkPage(account,new Page(page,size));
    }

    @PostMapping("/getParkDayAmount")
    public RestResult getParkDayAmount(@RequestBody String json,HttpServletRequest request){

        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        List<Date> dates = new ArrayList<>();
        Long areaId;
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            areaId = new Long(jsonObject.get("parkId").toString());
            if (areaId==null){
                return ResultUtils.error("区域id错误");
            }
            dates = JSONArray.parseArray(jsonObject.get("dates").toString(), Date.class);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.error("数据格式错误");
        }
        return adminService.areaAdminGetParkDaysAmount(dates,areaId,account);
    }



}
