package com.kaobei.service.impl;

import com.kaobei.commons.RestResult;
import com.kaobei.redis.RedisService;
import com.kaobei.security.config.WxLoginAuthenticationToken;
import com.kaobei.security.entity.MyUserDetails;
import com.kaobei.utils.JwtTokenUtils;
import com.kaobei.service.AuthService;
import com.kaobei.vo.AdminLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private RedisService redisService;

    @Override
    public RestResult adminLogin(AdminLoginVo adminLoginVo) {
        Authentication authentication;
        try {
            // 进行身份验证,
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(adminLoginVo.getUsername(), adminLoginVo.getPassword()));
        } catch (Exception e) {

            //设置登入密码错误限制
            redisService.setUserLoginLimit(adminLoginVo.getUsername());
            return new RestResult(0, e.getMessage(), null);
        }

        MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal();
        RestResult result = new RestResult(1, "登入成功", null);

        log.info("管理员:{} 已经登入。。。本次权限为:{}", loginUser.getUsername(), loginUser.getAuthorities().toString());

        //主动失效 设置黑名单 并关闭已存在socket
        if (redisService.userLogoutByServer(adminLoginVo.getUsername()) == 0) {
            return null;
        }

        result.put("token", jwtTokenUtils.generateToken(loginUser, "admin"));
        return result;
    }

    @Override
    public RestResult userWxLogin(String code) {
        Authentication authentication;
        try {
            // 进行身份验证,
            authentication = authenticationManager.authenticate(
                    new WxLoginAuthenticationToken(code, "vxLogin"));
        } catch (Exception e) {
            e.printStackTrace();
            return new RestResult(0, e.getMessage(), null);
        }

        MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal();
        RestResult result = new RestResult(1, "登入成功", null);

        log.info("用户:{} 已经登入。。。本次权限为:{}", loginUser.getUsername(), loginUser.getAuthorities().toString());


        //主动失效 设置黑名单 并关闭已存在socket
        if (redisService.userLogoutByServer(loginUser.getUsername()) == 0) {
            return null;
        }
        int i = 1/0;
        result.put("token", jwtTokenUtils.generateToken(loginUser, "user"));
        return result;
    }


}
