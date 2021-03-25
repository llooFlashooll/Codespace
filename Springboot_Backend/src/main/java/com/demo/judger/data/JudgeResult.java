package com.demo.judger.data;

import lombok.Data;


@Data
public class JudgeResult {

    private Integer submissionUsedTime;

    private Integer submissionUsedMemory;

    private String submissionJudgeResult;

    private String error;//编译错误时返回错误信息

}
