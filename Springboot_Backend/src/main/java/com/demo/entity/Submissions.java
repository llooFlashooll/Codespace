package com.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author w_57
 * @since 2020-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Submissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("submissionId")
    private Integer submissionId;

    @TableField("problemId")
    private Integer problemId;

    private String uid;

    @TableField("languageId")
    private Integer languageId;

    @TableField("submissionSubmitTime")
    private LocalDateTime submissionSubmitTime;

    @TableField("submissionUsedTime")
    private Integer submissionUsedTime;

    @TableField("submissionUsedMemory")
    private Integer submissionUsedMemory;

    @TableField("submissionJudgeResult")
    private String submissionJudgeResult;

    @TableField("submissionCode")
    private String submissionCode;


}
