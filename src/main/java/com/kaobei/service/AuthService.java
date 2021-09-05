package com.kaobei.service;

import com.kaobei.commons.RestResult;
import com.kaobei.vo.AdminVo;

public interface AuthService {

    RestResult userWxLogin(String code);

    RestResult adminLogin(AdminVo adminVo);
}
