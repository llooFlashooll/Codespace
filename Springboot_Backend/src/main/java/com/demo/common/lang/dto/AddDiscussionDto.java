package com.demo.common.lang.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class AddDiscussionDto {



    @Positive(message = "用户id不能为空")
    private Integer discussionUid;

    @Positive(message = "题目id不能为空")
    private Integer problemId;

    @NotBlank(message = "评论不能为空")
    private String content;
}
