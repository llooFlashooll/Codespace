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
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("blogId")
    private Integer blogId;

    @TableField("submitDate")
    private LocalDateTime submitDate;

    private String content;

    private String uid;

    private Integer status;

    @TableField("problemId")
    private Integer problemId;

    @TableField("blogTitle")
    private String blogTitle;


}
