package com.kaobei.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetParkVo {
    @Nullable
    private String keyword;

    @NotNull
    private long page;

    @NotNull
    private long size;
}
