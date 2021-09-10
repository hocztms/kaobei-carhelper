package com.kaobei.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaAmountDto {

    private Long areaId;
    private Double amount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date dayTime;
}
