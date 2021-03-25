package com.demo.common.lang.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VerifyCodeDto {

    @NotBlank(message = "邮箱不能为空!")
    String email;
}
