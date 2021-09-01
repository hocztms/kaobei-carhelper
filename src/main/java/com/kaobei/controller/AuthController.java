package com.kaobei.controller;

import com.kaobei.commons.RestResult;
import com.kaobei.security.jwt.JwtAuthService;
import com.kaobei.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@PreAuthorize("permitAll()")
@RestController
public class AuthController {

    @Autowired
    private JwtAuthService jwtAuthService;

    @RequestMapping("/wxLogin")
    public RestResult wxLogin(){
        return jwtAuthService.wxUserLogin("123");
    }
}
