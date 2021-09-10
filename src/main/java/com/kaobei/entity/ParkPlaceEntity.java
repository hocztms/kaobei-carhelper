package com.kaobei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_place")
@Builder
public class ParkPlaceEntity {

    @TableId(value = "place_id",type = IdType.AUTO)
    private Long placeId;

    private Long parkId;

    private String placeNumber;
    private Double lng;
    private Double lat;
    private Integer isOccupied;


    @Version
    private Integer version;
}
