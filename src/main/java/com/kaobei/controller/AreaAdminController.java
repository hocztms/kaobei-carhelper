package com.kaobei.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.commons.RestResult;
import com.kaobei.service.AreaAdminService;
import com.kaobei.utils.JwtTokenUtils;
import com.kaobei.vo.ParkAdminVo;
import com.kaobei.vo.ParkVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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


    @GetMapping("/getAreaParkPage")
    public RestResult getAreaParkPage(long page,long size,HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return adminService.areaAdminGetAreaParkPage(account,new Page(page,size));
    }


}
