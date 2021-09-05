package com.kaobei.controller;


import com.kaobei.commons.RestResult;
import com.kaobei.vo.AreaVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/boss")
@PreAuthorize("hasAuthority('boss')")
@RestController
@Slf4j
public class BossAdminController {



    @PostMapping("/createArea")
    public RestResult bossCreateArea(@Validated  @RequestBody  AreaVo areaVo){
        return null;
    }
}
