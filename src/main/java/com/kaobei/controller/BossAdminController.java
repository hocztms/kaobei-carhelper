package com.kaobei.controller;


import com.kaobei.commons.RestResult;
import com.kaobei.service.BossAdminService;
import com.kaobei.vo.AreaAdminVo;
import com.kaobei.vo.AreaVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/boss")
@PreAuthorize("hasAuthority('boss')")
@RestController
@Slf4j
public class BossAdminController {

    @Autowired
    private BossAdminService bossAdminService;


    @PostMapping("/createArea")
    public RestResult bossCreateArea(@Validated  @RequestBody  AreaVo areaVo){
        return bossAdminService.bossCreateArea(areaVo);
    }

    @PostMapping("/createAreaAdmin")
    public RestResult createAreaAdmin(@Validated  @RequestBody AreaAdminVo areaAdminVo){
        return bossAdminService.bossCreateAreaAdmin(areaAdminVo);
    }

    @GetMapping("/getAreaPage")
    public RestResult createAreaAdmin(long page,long size){
        return bossAdminService.bossGetAreaPage(page,size);
    }
}
