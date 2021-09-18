package com.kaobei.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.commons.RestResult;
import com.kaobei.service.ParkAdminService;
import com.kaobei.utils.JwtTokenUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.PlaceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequestMapping("/park")
@PreAuthorize("hasAuthority('parkAdmin')")
@RestController
@Slf4j
public class ParkAdminController {


    @Autowired
    private ParkAdminService parkAdminService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;



    @PutMapping("/freezePark")
    public RestResult freezePark(HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return parkAdminService.parkAdminFreezePark(account);
    }

    @PutMapping("/unfreezePark")
    public RestResult unfreezePark(HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return parkAdminService.parkAdminUnFreezePark(account);
    }

    @PostMapping("/createPlace")
    public RestResult createPlace(@Validated  @RequestBody PlaceVo placeVo, HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return parkAdminService.parkAdminAddParkPlace(placeVo,account);
    }

    @GetMapping("/getPlacePage")
    public RestResult getPlacePage(long page,long size, HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return parkAdminService.parkAdminGetParkPlacePage(new Page(page,size),account);
    }

    @PostMapping("/getDayRecords")
    public RestResult getDayRecords(@RequestBody String json,HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        Date date = new Date();
        Long page;
        Long size;
        try {

            JSONObject jsonObject = JSONObject.parseObject(json);
            date = jsonObject.getObject("date",Date.class);
            page = new Long(jsonObject.get("page").toString());
            size = new Long(jsonObject.get("size").toString());
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.error("日期格式错误");
        }

        return parkAdminService.parkAdminGetDateRecords(date,new Page(page,size),account);
    }


    @GetMapping("/getComplaint")
    public RestResult getComplaint(long page,long size,HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);

        return parkAdminService.parkAdminGetComplaintPage(account,new Page(page,size));
    }

    @PutMapping("/handleComplaint")
    public RestResult handleComplaint(@RequestBody List<Long> ids, HttpServletRequest request){
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        return parkAdminService.parkAdminHandleComplaints(ids,account);
    }

}
