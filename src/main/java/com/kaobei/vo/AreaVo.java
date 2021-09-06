package com.kaobei.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaVo {

    @NotNull(message = "不能为空")
    private Long areaId;


    @NotBlank(message = "areaName不能为空")
    @Size(max = 30,message = "最长不超过30字")
    private String areaName;

}
