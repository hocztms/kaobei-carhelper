package com.kaobei.controller;
import com.alibaba.fastjson.JSONObject;
import com.kaobei.commons.RestResult;
import com.kaobei.service.AuthService;
import com.kaobei.utils.AuthUtils;
import com.kaobei.utils.JwtTokenUtils;
import com.kaobei.utils.ResultUtils;
import com.kaobei.vo.AdminLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@PreAuthorize("permitAll()")
@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AuthUtils authUtils;

    /*
    wx小程序登入接口
     */
    @PostMapping("/userWxLogin")
    public RestResult wxLogin(@RequestBody String json) {
        String code;
        try {
            code = (String) JSONObject.parseObject(json).get("code");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error("数据格式错误");
        }

        return authService.userWxLogin(code);
    }

    /*
    后台管理员登入接口
     */
    @PostMapping("/adminLogin")
    public RestResult adminLogin(@Validated @RequestBody AdminLoginVo adminLoginVo) {
        return authService.adminLogin(adminLoginVo);
    }

    /*
    获取token 信息示例
     */
    @GetMapping("/testToken")
    public void getTokenTest(HttpServletRequest request) {
        //不同权限方法下 获取的是openId 或者管理员用户名
        String account = jwtTokenUtils.getAuthAccountFromRequest(request);
        log.info("当前登入用户为:{}", account);
        String test_account = authUtils.getContextUserDetails().getUsername();
        log.info("当前登入用户为:{}", test_account);
    }
}

