package com.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Problems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("problemId")
    private Integer problemId;

    @TableField("problemName")
    private String problemName;

    @TableField("problemTimeLimit")
    private Integer problemTimeLimit;

    @TableField("problemMemoryLimit")
    private Integer problemMemoryLimit;

    @TableField("problemDescription")
    private String problemDescription;

    @TableField("problemInputFormat")
    private String problemInputFormat;

    @TableField("problemOutputFormat")
    private String problemOutputFormat;

    @TableField("problemSampleInput")
    private String problemSampleInput;

    @TableField("problemSampleOutput")
    private String problemSampleOutput;

    @TableField("problemHint")
    private String problemHint;

    @TableField("problemCheckPointCnt")
    private Integer problemCheckPointCnt;

    @TableField("problemSubmitCnt")
    private Integer problemSubmitCnt;

    @TableField("problemAcceptCnt")
    private Integer problemAcceptCnt;


}
