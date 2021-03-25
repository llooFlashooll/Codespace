package com.demo.common.lang.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
public class SaveBlogDto {

    @PositiveOrZero(message = "博客编号错误")
    private Integer blogId;

    @NotBlank(message = "博客内容不能为空")
    private String content;

    @NotBlank(message = "用户id不能为空")
    private String uid;

    @NotBlank(message = "标题不能为空")
    private String blogTitle;



    private Integer problemId;
}
