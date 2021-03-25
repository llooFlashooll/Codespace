package com.demo.common.lang.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "邮箱不能为空")
    private String email;


    @NotBlank(message = "密码不能为空")
    private String password;


}
