package com.kaobei.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkRecoedAbleDto {
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
