package com.kaobei.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceVo {



    @NotEmpty(message = "对象列表不能为空")
    @Size(max = 3,min = 3,message = "大小为3")
    @Valid
    List<Device> list;

    @Data
    public static class Device{

        @NotBlank(message = "机器编号不能为空")
        private String deviceNumber;

        @NotNull(message = "距离不能为空")
        private Double distance;
    }
}
