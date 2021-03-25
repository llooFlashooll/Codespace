package com.demo.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.entity.Submissions;
import com.demo.service.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author w_57
 * @since 2020-11-18
 */
@RestController
public class SubmissionsController {

    @Autowired
    SubmissionsService submissionsService;


    @GetMapping("/statusList")
    public Result getStatusList(@RequestParam("pagenum") int pagenum,
                                 @RequestParam("pagesize")int pagesize,
                                 @RequestParam("query") String query){
        List<Submissions> retList=new ArrayList<>();

        int total=submissionsService.count();
        if(query.length()>0)
        {
            retList=submissionsService.list(new QueryWrapper<Submissions>().eq("problemId",query));
            Collections.reverse(retList);
        }
        if(retList.size()==0)
        {
            List<Submissions> submissionsList=submissionsService.list();
            Collections.reverse(submissionsList);
            //String jsonProblemList= JSON.toJSONString(problemList);

            for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
                retList.add(submissionsList.get(i));
            }
        }
        else {
            total = retList.size();
        }

        return Result.succ(MapUtil.builder()
                .put("statusList",retList)
                .put("total",total)
                .map());
    }


    @GetMapping("/status")
    public Result getStatus(@RequestParam("userId") String userId,
                            @RequestParam("pagenum") int pagenum,
                            @RequestParam("pagesize")int pagesize){
        List<Submissions> submissionsList=submissionsService.list(new QueryWrapper<Submissions>().eq("uid",userId));
        Collections.reverse(submissionsList);
        List<Submissions> retList=new ArrayList<>();
        int total=submissionsList.size();
        for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
            retList.add(submissionsList.get(i));
        }

        return Result.succ(MapUtil.builder()
                .put("statusList",retList)
                .put("total",total)
                .map());
    }

    @GetMapping("/getStatusById")
    public Result getStatusById(@RequestParam("submissionId")int submissionId){
        Submissions submission=submissionsService.getById(submissionId);
        return Result.succ(submission);
    }



}
