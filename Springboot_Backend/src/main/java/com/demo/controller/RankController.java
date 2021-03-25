package com.demo.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.entity.Submissions;
import com.demo.service.SubmissionsService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RankController {

    @Autowired
    UserService userService;

    @Autowired
    SubmissionsService submissionsService;

    @GetMapping("allRank")
    public Result allRank(@RequestParam("pagenum") int pagenum,
                          @RequestParam("pagesize")int pagesize){
        int count=userService.count();
        List<Map> list=new ArrayList<>();

        for(int i=1;i<=count;i++)
        {
            int cnt=submissionsService.list(new QueryWrapper<Submissions>().select("distinct problemId")
                    .eq("uid",i)
                    .eq("submissionJudgeResult","Accept")).size();
            String username=userService.getById(i).getUsername();
            list.add(MapUtil.builder()
                    .put("userid",i)
                    .put("username",username)
                    .put("acceptCnt",cnt)
                    .map());
        }
        Collections.sort(list, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                if(((Integer)o1.get("acceptCnt"))>((Integer)o2.get("acceptCnt"))) {
                    return -1;
                } else if(((Integer)o1.get("acceptCnt"))<((Integer)o2.get("acceptCnt"))) {
                    return 1;
                } else {
                    return 0;
                }

            }
        });


        int total=list.size();
        List<Map> retList=new ArrayList<>();

        for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
            retList.add(list.get(i));
        }

        return Result.succ(MapUtil.builder()
                .put("rankList",retList)
                .put("total",total)
                .map());
    }

}
