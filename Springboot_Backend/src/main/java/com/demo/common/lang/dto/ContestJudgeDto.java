package com.demo.common.lang.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ContestJudgeDto {
    @NotBlank(message = "请选择语言")
    private String language;

    @PositiveOrZero(message = "请选择语言")
    private Integer languageId;

    private int timeLimit;

    private int checkpoint_cnt;

    @Positive(message = "请选择要提交的题目")
    private Integer problem_id;

    @NotBlank(message = "用户id为空")
    private String uid;

    @NotBlank(message = "不允许提交空代码")
    private String submissionCode;

    @Positive(message = "请选择比赛")
    private int contestId;
}
