package com.kaobei.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintVo {

    @NotNull(message = "parkId不能为空")
    private Long parkId;

    @NotBlank(message = "反馈信息不能为空")
    @Size(max = 200,message = "最多不能超过200字")
    private String content;
}
