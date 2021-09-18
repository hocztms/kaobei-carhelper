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
public class ParkVo {

    @NotNull(message = "停车场id不能为空")
    private Long parkId;

    @NotBlank(message = "停车场名字不能为空")
    @Size(max = 30,message = "最长不能超过30字")
    private String parkName;
    @NotNull(message = "经度不能为空")
    private Double lng;
    @NotNull(message = "伟度不能为空")
    private Double lat;

    @NotNull(message = "每小时计费")
    private Double charge;
}
