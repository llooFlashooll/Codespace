package com.demo.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.entity.Submissions;
import com.demo.entity.User;
import com.demo.service.SubmissionsService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author w_57
 * @since 2020-11-17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    SubmissionsService submissionsService;

    @GetMapping("/index")
    public Result index(@RequestParam("userId") String userId) {
        User user = userService.getById(userId);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        userService.save(user);

        return Result.succ(user);
    }

    @GetMapping("/accept")
    public Result accept(@RequestParam("userId") String userId){
        List<Submissions> submissions=submissionsService.list(new QueryWrapper<Submissions>().select("distinct problemId")
                .eq("uid",userId)
                .eq("submissionJudgeResult","Accept"));
        List<Integer> ret=new ArrayList<>();
        for(Submissions s :submissions) {
            ret.add(s.getProblemId());
        }
        return Result.succ(MapUtil.builder()
                .put("acceptList",ret)
                .map());

    }

}
