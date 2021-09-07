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
@Builder
@TableName(value = "tb_park")
public class ParkEntity {

    @TableId(value = "park_id",type = IdType.AUTO)
    private Long parkId;
    private Long areaId;
    private String parkName;
    private Double lng;
    private Double lat;

    private Integer placeNum;
    private Integer parkPlaceSum;

    private Integer status;

    @Version
    private Integer version;
}
