package com.demo.common.lang.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class AddProblemDto {


    @NotBlank(message = "题目名字不能为空")
    private String problemName;

    @Positive(message = "时限不合理")
    private Integer problemTimeLimit;

    @Positive(message = "时限不合理")
    private Integer problemMemoryLimit;

    @NotBlank(message = "不能为空")
    private String problemDescription;

    @NotBlank(message = "不能为空")
    private String problemInputFormat;

    @NotBlank(message = "不能为空")
    private String problemOutputFormat;

    @NotBlank(message = "不能为空")
    private String problemSampleInput;

    @NotBlank(message = "不能为空")
    private String problemSampleOutput;

    private String problemHint;



}
