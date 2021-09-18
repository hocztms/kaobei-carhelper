package com.kaobei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_common_park")
public class CommonParkEntity {
    @TableId(value = "common_id",type = IdType.AUTO)
    private Long commonId;
    private String openId;
    private Long parkId;
    private String parkName;
    private Long times;
}
