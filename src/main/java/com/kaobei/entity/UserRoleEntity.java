package com.kaobei.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_userRole")
public class UserRoleEntity {

    private String openId;
    private String role;
}
