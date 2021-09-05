package com.kaobei.controller;

import com.kaobei.commons.RestResult;
import com.kaobei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/user")
@PreAuthorize("permitAll()")
@RestController
public class UserFileController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getPlateByPicture")
    public RestResult getPlateByPicture(String fileName){
        return userService.getPlateByPicture(fileName);
    }
    //@CrossOrigin
    @RequestMapping(value = "/downLoad")
    public RestResult doneLoad(MultipartFile file){
        return userService.doneLoad(file);
    }
}
