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
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("contestId")
    private Integer contestId;

    @TableField("contestName")
    private String contestName;

    @TableField("contestNotes")
    private String contestNotes;

    @TableField("contestStartTime")
    private LocalDateTime contestStartTime;

    @TableField("contestEndTime")
    private LocalDateTime contestEndTime;

    @TableField("contestProblems")
    private String contestProblems;


}
