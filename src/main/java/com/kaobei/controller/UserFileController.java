package com.kaobei.controller;

import com.kaobei.commons.RestResult;
import com.kaobei.service.UserService;
import com.kaobei.vo.DownLodeVo;
import com.kaobei.vo.GetPlateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/user/file/")
@PreAuthorize("hasAuthority('user')")
@RestController
public class UserFileController {
    @Autowired
    private UserService userService;

    @PostMapping( "/getPlateByPicture")
    public RestResult getPlateByPicture(@Validated @RequestBody GetPlateVo getPlateVo){
        return userService.getPlateByPicture(getPlateVo);
    }
    //@CrossOrigin
    @PostMapping( "/downLoad")
    public RestResult doneLoad(@Validated  @RequestBody DownLodeVo downLodeVo){
        return userService.doneLoad(downLodeVo);
    }
}
