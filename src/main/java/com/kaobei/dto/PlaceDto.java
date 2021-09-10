package com.kaobei.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {

    //停车场标识位置
    private Long placeId;
    private String placeNumber;

    //物理位置
    private Double lng;
    private Double lat;

    private Integer isOccupied;

}
