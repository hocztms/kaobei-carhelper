package com.kaobei.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_complaint")
public class ComplaintEntity {

    @TableId(value = "complaint_id")
    private Long complaint_id;

    private String openId;

    private Long parkId;

    private String content;

    private Integer status;
}
