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
 * @since 2020-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Discussion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("discussionId")
    private Integer discussionId;

    @TableField("discussionUid")
    private Integer discussionUid;

    @TableField("problemId")
    private Integer problemId;

    @TableField("discussionTime")
    private String discussionTime;

    private String content;


}
