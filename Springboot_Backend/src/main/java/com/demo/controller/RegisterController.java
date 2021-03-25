package com.demo.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.demo.common.lang.Result;
import com.demo.common.lang.dto.RegisterDto;
import com.demo.common.lang.dto.VerifyCodeDto;
import com.demo.entity.User;
import com.demo.service.UserService;
import com.demo.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterDto registerDto){

        //这里要检查一下邮箱格式
        if(!registerDto.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")){
            return Result.fail("邮箱格式错误!");
        }
        //用户id从1开始递增
        Integer userCnt= userService.count()+1;

        User user = new User();
        //注册信息传给用户
        BeanUtil.copyProperties(registerDto,user);
        user.setId(userCnt.toString());

        userService.save(user);

        return Result.succ(user);
    }

    //返回验证码到前端
    @PostMapping("/getVerifyCode")
    public Result getVerifyCode(@Validated @RequestBody VerifyCodeDto verifyCodeDto){

        //这里要检查一下邮箱格式
        if(!verifyCodeDto.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")){
            return Result.fail("邮箱格式错误!");
        }

        if(userService.query().eq("email",verifyCodeDto.getEmail()).count()>0){
            return Result.fail("邮箱已被注册!");
        }
        int val=(int)(Math.random()*999999);
        String code=String.valueOf(val);
        new Thread(new MailUtil(verifyCodeDto.getEmail(),code,"激活邮件")).start();
        return Result.succ(
                MapUtil.builder()
                .put("verifyCode", code)
                        .map()
                ,"验证码获取成功");
    }



}
