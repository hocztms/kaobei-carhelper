package com.kaobei.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName(value = "tb_adminRole")
public class AdminRoleEntity {

    private String username;
    private String role;
}
