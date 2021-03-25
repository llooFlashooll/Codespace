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
 * @since 2020-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Languages implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("languageId")
    private Integer languageId;

    @TableField("languageName")
    private String languageName;

    @TableField("languageCompileCommand")
    private String languageCompileCommand;

    @TableField("languageRunCommand")
    private String languageRunCommand;


}
