package com.kaobei.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AreaAdminVo {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 20)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(max = 20)
    private String password;

}
