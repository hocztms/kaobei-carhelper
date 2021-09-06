package com.kaobei.service;

import com.kaobei.commons.RestResult;
import com.kaobei.vo.AdminLoginVo;

public interface AuthService {

    RestResult userWxLogin(String code);

    RestResult adminLogin(AdminLoginVo adminLoginVo);
}
