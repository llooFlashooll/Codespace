package com.demo.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.common.lang.dto.AddContestDto;
import com.demo.entity.*;
import com.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author w_57
 * @since 2020-12-15
 */
@RestController

public class ContestController {

    @Autowired
    ContestService contestService;

    @Autowired
    ProblemsService problemsService;

    @Autowired
    ContestContestantsService contestContestantsService;

    @Autowired
    ContestSubmissionsService contestSubmissionsService;

    @Autowired
    SubmissionsService submissionsService;


    @PostMapping("/addContest")
    public Result addContest(@Validated @RequestBody AddContestDto addContestDto){
        System.out.println(addContestDto);
        Contest contest=new Contest();
        BeanUtil.copyProperties(addContestDto,contest);
        contest.setContestId(contestService.count()+1);
        contestService.save(contest);
        return Result.succ(null);
    }

    @GetMapping("/allContest")
    public Result allContest(@RequestParam("pagenum") int pagenum,
                             @RequestParam("pagesize")int pagesize,
                             @RequestParam("userid")int userid){
        List<Contest> contestList=contestService.list();
        Collections.reverse(contestList);
        List<Contest> retList=new ArrayList<>();
        int total=contestList.size();
        for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){

            retList.add(contestList.get(i));
        }
        List<Integer> statusList=new ArrayList<>();

        for(int i=0;i<retList.size();i++)
        {
            if(LocalDateTime.now().plusMinutes(1)
                    .isAfter(retList.get(i).getContestEndTime())){
                statusList.add(1);//结束了
            }else if(LocalDateTime.now().plusMinutes(1)
                    .isAfter(retList.get(i).getContestStartTime())){
                statusList.add(-1);//开始了
            }
            else{
                statusList.add(0);//没开始
            }


        }
        List<Boolean> participateStatusList=new ArrayList<>();
        for(int i=0;i<retList.size();i++)
        {
            if(contestContestantsService.list(new QueryWrapper<ContestContestants>()
            .eq("contestId",retList.get(i).getContestId())
            .eq("contestantUid",userid)).size()>0){
                participateStatusList.add(true);//参加了
            }else{
                participateStatusList.add(false);
            }

        }


        return Result.succ(MapUtil.builder()
                .put("contestList",retList)
                .put("statusList",statusList)
                .put("participateStatusList",participateStatusList)
                .put("total",total)
                .map());
    }


    @GetMapping("/contest/problem")
    public Result contestProblem(@RequestParam("contestId")int contestId){
        Contest contest=contestService.getById(contestId);
        List<Problems> problemsList=new ArrayList<>();

        String[] list=contest.getContestProblems().split(",");
        //System.out.println(contest.getContestProblems());
        //System.out.println(list);
        for(int i=0;i<list.length;i++){
            //System.out.println(Integer.getInteger(list[i]));
            problemsList.add(problemsService.getById(list[i]));
        }
        return Result.succ(MapUtil.builder()
        .put("problemsList",problemsList)
        .map());
    }

    @GetMapping("/contest/rank")
    public Result getStatusList(@RequestParam("pagenum") int pagenum,
                                @RequestParam("pagesize")int pagesize,
                                @RequestParam("contestId")int contestId){


        List<ContestSubmissions> contestSubmissionsList=contestSubmissionsService
                .list(new QueryWrapper<ContestSubmissions>().eq("contestId",contestId));

        Collections.reverse(contestSubmissionsList);

        List<Submissions> submissionsList=new ArrayList<>();

        List<ContestContestants> userList=contestContestantsService
                .list(new QueryWrapper<ContestContestants>().eq("contestId",contestId));
        int total=userList.size();
       // System.out.println(total);
        List<Map> allList=new ArrayList<>();
        for(int i=0;i<total;i++){

            HashSet<Integer> acceptSet=new HashSet<>();
            for(int j=0;j<contestSubmissionsList.size();j++){
                Submissions submissions=submissionsService.getById(contestSubmissionsList.get(j).getSubmissionId());
                if(!submissions.getUid().equals(userList.get(i).getContestantUid().toString())){
                    continue;
                }
                if("Accept".equals(submissions.getSubmissionJudgeResult())){
                    acceptSet.add(submissions.getProblemId());
                }
            }
            String acceptList=acceptSet.toString();
            allList.add(MapUtil.builder()
                    .put("acceptList",acceptList)
                    .put("userid",userList.get(i).getContestantUid())
                    .map());
        }

        List<Map> retList=new ArrayList<>();

        if(total==0){
            return Result.succ(MapUtil.builder()
                    .put("statusList",retList)
                    .put("total",total)
                    .map());
        }
        for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
            retList.add(allList.get(i));
        }

        return Result.succ(MapUtil.builder()
                .put("statusList",retList)
                .put("total",total)
                .map());
    }

}
