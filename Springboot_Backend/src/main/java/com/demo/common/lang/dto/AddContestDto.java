package com.demo.common.lang.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class AddContestDto {

    @NotBlank(message = "题目名字不能为空")
    String contestName;


    private String contestNotes;

    private LocalDateTime contestStartTime;

    private LocalDateTime contestEndTime;

    @NotBlank(message = "不能为空")
    private String contestProblems;
}
