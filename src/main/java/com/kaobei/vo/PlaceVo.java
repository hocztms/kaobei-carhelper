package com.kaobei.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceVo {
    @NotBlank(message = "停车场标签位置不能为空")
    @Size(max = 30,message = "最大不超过30 位")
    private String placeNumber;

    @NotNull(message = "经纬度不允许为空")
    private Double lng;

    @NotNull(message = "经纬度不允许为空")
    private Double lat;
}
