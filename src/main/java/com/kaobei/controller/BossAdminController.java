package com.kaobei.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.commons.RestResult;
import com.kaobei.service.BossAdminService;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.AreaAdminVo;
import com.kaobei.vo.AreaVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/boss")
@PreAuthorize("hasAuthority('boss')")
@RestController
@Slf4j
public class BossAdminController {

    @Autowired
    private BossAdminService bossAdminService;


    /*
    创建 区域
     */
    @PostMapping("/createArea")
    public RestResult bossCreateArea(@Validated  @RequestBody  AreaVo areaVo){
        return bossAdminService.bossCreateArea(areaVo);
    }

    /*
   获取区域 分页数据
    */
    @GetMapping("/getAreaPage")
    public RestResult createAreaAdmin(long page,long size){
        return bossAdminService.bossGetAreaPage(new Page(page,size));
    }

    /*
    创建 区域管理员
     */
    @PostMapping("/createAreaAdmin")
    public RestResult createAreaAdmin(@Validated  @RequestBody AreaAdminVo areaAdminVo){
        return bossAdminService.bossCreateAreaAdmin(areaAdminVo);
    }


    @GetMapping("/getAreaAdmin")
    public RestResult getAreaAdmin(long areaId){
        return bossAdminService.bossGetAreaAdminList(areaId);
    }


    @PostMapping("/getAreaDayAmount")
    public RestResult getTestTime(@RequestBody String json){
        System.out.println(json);
        List<Date> dates = new ArrayList<>();
        Long areaId;
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            areaId = new Long(jsonObject.get("areaId").toString());
            if (areaId==null){
                return ResultUtils.error("区域id错误");
            }
            dates = JSONArray.parseArray(jsonObject.get("dates").toString(), Date.class);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.error("数据格式错误");
        }
        return bossAdminService.bossGetAreaDaysCostAmount(dates,areaId);
    }
}
