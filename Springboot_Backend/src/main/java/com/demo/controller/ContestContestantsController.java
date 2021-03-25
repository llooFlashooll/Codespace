package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.entity.ContestContestants;
import com.demo.service.ContestContestantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author w_57
 * @since 2020-12-15
 */
@RestController

public class ContestContestantsController {

    @Autowired
    ContestContestantsService contestContestantsService;

    @GetMapping("/participate")
    public Result participate(@RequestParam("userid")int userid,
                              @RequestParam("contestid")int contestid){
        int cnt=contestContestantsService
                .list(new QueryWrapper<ContestContestants>()
                        .eq("contestId",contestid)
                        .eq("contestantUid",userid)).size();
        System.out.println(cnt);
        if(cnt!=0){
            return Result.fail("已经报名过了");
        }
        ContestContestants contestContestants=new ContestContestants();
        contestContestants.setContestantUid(userid);
        contestContestants.setContestId(contestid);
        contestContestantsService.save(contestContestants);
        return Result.succ(null);
    }
}
