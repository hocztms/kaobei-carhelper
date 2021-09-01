package com.kaobei.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName(value = "tb_user")
public class UserEntity {

    private String openId;
    private String carNumber;
    private int amount;
}
