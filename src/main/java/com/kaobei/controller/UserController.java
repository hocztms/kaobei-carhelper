package com.kaobei.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.commons.RestResult;
import com.kaobei.service.ParkRecordService;
import com.kaobei.service.ParkService;
import com.kaobei.service.UserService;
import com.kaobei.utils.JwtTokenUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@PreAuthorize("hasAuthority('user')")
@RestController
public class UserController {
    @Autowired
    private ParkService parkService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private ParkRecordService parkRecordService;

    @PostMapping( "/getNearPark")
    public RestResult getNearPark(@Validated @RequestBody GetNearParkVo getNearParkVo){
        return ResultUtils.success(parkService.findParkListByPosNear(getNearParkVo.getLng(),getNearParkVo.getLat(),getNearParkVo.getRadius(),getNearParkVo.getCount()));
    }

    @PostMapping( "/getPark")
    public RestResult getPark(@Validated @RequestBody GetParkVo getParkVo){
        return ResultUtils.success(parkService.findParkByKeyword(getParkVo.getKeyword(),new Page(getParkVo.getPage(),getParkVo.getSize())));
    }

    @PostMapping( "/setPlate")
    public RestResult setPlate(HttpServletRequest request, @Validated @RequestBody SetPlateVo setPlateVo){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        //这边要验证
        return ResultUtils.success(userService.setPlate(setPlateVo,account));
    }
    @PostMapping( "/getPlate")
    public RestResult getPlate(HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return ResultUtils.success(userService.getPlate(account));
    }
    @PostMapping( "/verifyPark")
    public RestResult verifyPark(HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return ResultUtils.success(parkRecordService.getUserIsParkByOpenId(account));
    }


    /*
    用户抢车位接口
     */
    @PostMapping( "/grabPark")
    public RestResult grabPark(@Validated @RequestBody GrabParkVo grabParkVo,HttpServletRequest request) {
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return userService.userGrabParkPlace(grabParkVo.getParkId(), account);
    }

    @DeleteMapping("/cancelPark")
    public RestResult cancelPark(HttpServletRequest request){

        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return userService.userCancelPark(account);
    }

    /*
    室内定位距离
     */
    @PostMapping( "/getPlaceDis")
    public RestResult getPlaceDis(@Validated @RequestBody DeviceVo deviceVo, HttpServletRequest request) {
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);

        return userService.userGetParkPlaceDis(deviceVo,account);
    }


    /*
    停车接口
     */
    @PostMapping( "/parking")
    public RestResult parking(@Validated @RequestBody DeviceVo deviceVo, HttpServletRequest request) {
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);

        return userService.userParking(deviceVo,account);
    }

    /*
    停车结束接口
     */

    @PutMapping( "/endPark")
    public RestResult endPark( HttpServletRequest request) {
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);

        return userService.userEndPark(account);
    }

    /*
    用户反馈接口
     */

    @PostMapping( "/feedBack")
    public RestResult feedBack(@Validated@RequestBody ComplaintVo complaintVo,HttpServletRequest request) {
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);

        return userService.userFeedBack(complaintVo,account);
    }
}
