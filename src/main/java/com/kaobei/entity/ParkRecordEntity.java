package com.kaobei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_parkRecord")
public class ParkRecordEntity {

    @TableId(value = "record_id",type = IdType.AUTO)
    private Long recordId; //记录id
    private Long areaId; //区域id
    private Long parkId; //停车场id
    private Long placeId; // 车位id
    private String openId; // openId


    private Date startTime;
    private Date endTime;

    private Double cost;
    private Integer status;
    private Integer deleted;
}
