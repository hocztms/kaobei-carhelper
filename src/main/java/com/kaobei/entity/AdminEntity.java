package com.kaobei.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_admin")
@Builder
public class AdminEntity {

    private String username;
    private String password;
    private long areaId;
    private int status;
}
