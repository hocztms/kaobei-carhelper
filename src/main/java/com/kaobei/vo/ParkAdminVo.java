package com.kaobei.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkAdminVo {

    @Pattern(regexp = "^[\u4e00-\u9fa5_a-zA-Z0-9]+$", message = "用户名只能有中文,数字,字母")
    @Size(min = 3, max = 20, message = "用户名 6-20位")
    private String username;

    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}", message = "密码长度限制6-20位并且至少包含一个数字或一个大写字母或一个小写字母")
    private String password;

    @NotNull(message = "停车场id不能为空")
    private Long parkId;

}