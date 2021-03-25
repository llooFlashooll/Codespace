package com.demo.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.common.lang.dto.LoginDto;
import com.demo.entity.User;
import com.demo.service.UserService;
import com.demo.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){

        //User user=userService.getOne(new QueryWrapper<User>().eq("username",loginDto.getUsername()));
        User user=userService.getOne(new QueryWrapper<User>().eq("email",loginDto.getEmail()));
        Assert.notNull(user,"用户不存在");

        if(!user.getPassword().equals(loginDto.getPassword()))
        {
            return Result.fail("密码不正确");
        }

//        String jwt=jwtUtils.generateToken(user.getId());
//        response.setHeader("Authorization",jwt);
//        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("userid",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("email",user.getEmail())
                .map()
        );

    }

    @GetMapping("/getPasswordByEmail")
    public Result getPasswordByEmail(@RequestParam("email")String email){
        User user=userService.getOne(new QueryWrapper<User>().eq("email",email));
        if(user==null) {
            return Result.fail("用户不存在");
        }
        new Thread(new MailUtil(email,user.getPassword(),"密码找回")).start();
        return Result.succ(null,"包括密码的邮箱已发送到"+email);
    }

    //@RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        //SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
