package com.kaobei.security.config;


import com.kaobei.security.entity.MyUserDetails;
import com.kaobei.security.servie.MyWxUserDetailServiceImpl;
import com.kaobei.utils.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
//自定义 微信登入逻辑
public class WxLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyWxUserDetailServiceImpl userDetailService;
    @Autowired
    private WxUtils wxUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        String code = authentication.getName();// 这个获取表单输入中返回的用户名;

        String openId =null;

        //获取openId
        try {
            openId = wxUtils.getOpenIdByCode(code);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("无效code");
        }


        MyUserDetails userDetails = (MyUserDetails) userDetailService.loadUserByUsername(openId);


        return new WxLoginAuthenticationToken(userDetails, "vxLogin", userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {

        /**
         * providerManager会遍历所有
         * securityconfig中注册的provider集合
         * 根据此方法返回true或false来决定由哪个provider
         * 去校验请求过来的authentication
         */
        return (WxLoginAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

}
