package com.kaobei.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    private Long areaId;
    private Long parkId;
    private Integer status;

    @TableField(value = "is_area_admin")
    private Integer isAreaAdmin;

    @TableField(value = "is_park_admin")
    private Integer isParkAdmin;
}
