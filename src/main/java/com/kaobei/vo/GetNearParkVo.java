package com.kaobei.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNearParkVo {
    @NotNull
    private Double lng;

    @NotNull
    private Double lat;

    @NotNull
    private  Double radius;

    @NotNull
    private Integer count;
}
