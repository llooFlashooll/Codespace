package com.demo.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.common.lang.Result;
import com.demo.common.lang.dto.ContestJudgeDto;
import com.demo.common.lang.dto.JudgeDto;
import com.demo.entity.ContestSubmissions;
import com.demo.entity.Problems;
import com.demo.entity.Submissions;
import com.demo.judger.CompileAndJudge;
import com.demo.judger.data.JudgeResult;
import com.demo.service.ContestSubmissionsService;
import com.demo.service.ProblemsService;
import com.demo.service.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
 * @since 2020-12-15
 */
@RestController

public class ContestSubmissionsController {

    @Autowired
    SubmissionsService submissionsService;

    @Autowired
    ProblemsService problemsService;

    @Autowired
    ContestSubmissionsService contestSubmissionsService;

    @PostMapping("/contest/judge")
    public Result judge(@Validated @RequestBody ContestJudgeDto contestJudgeDto){

//        System.out.println(judgeDto.getSubmissionCode());
//        return Result.succ(null);


        if(contestJudgeDto.getSubmissionCode().contains("ystem")){


            return Result.succ(MapUtil.builder()
                    .put("submissionJudgeResult","code is killed")
                    .put("error","")
                    .map()
            );
        }
        Problems problems=problemsService.getById(contestJudgeDto.getProblem_id());
        contestJudgeDto.setCheckpoint_cnt(problems.getProblemCheckPointCnt());
        contestJudgeDto.setTimeLimit(problems.getProblemTimeLimit());

        // System.out.println(judgeDto);

        Integer submission_cnt=submissionsService.count()+1;
        Submissions submissions=new Submissions();

        {
            submissions.setSubmissionId(submission_cnt);

            submissions.setLanguageId(contestJudgeDto.getLanguageId());
            submissions.setProblemId(contestJudgeDto.getProblem_id());
            submissions.setSubmissionCode(contestJudgeDto.getSubmissionCode());
            submissions.setSubmissionJudgeResult("judging");


            // System.out.println(submissions);
            submissions.setUid(contestJudgeDto.getUid());


        }
        submissionsService.save(submissions);

        JudgeResult judgeResult= CompileAndJudge.compileAndJudge(
                submission_cnt,
                contestJudgeDto.getLanguage(),
                contestJudgeDto.getTimeLimit(),
                contestJudgeDto.getCheckpoint_cnt(),
                contestJudgeDto.getProblem_id(),
                contestJudgeDto.getSubmissionCode()
        );



        {



            submissions.setSubmissionJudgeResult(judgeResult.getSubmissionJudgeResult());

            submissions.setSubmissionUsedMemory(judgeResult.getSubmissionUsedMemory());
            submissions.setSubmissionUsedTime(judgeResult.getSubmissionUsedTime());
            // System.out.println(submissions);


            // System.out.println(submissions);

        }

        if("Compile Error".equals(judgeResult.getSubmissionJudgeResult())){
            submissions.setSubmissionCode(contestJudgeDto.getSubmissionCode()+"\n\n\n"+judgeResult.getError());
            System.out.println("CE!!!!");
        }
        submissionsService.saveOrUpdate(submissions);

        problems.setProblemSubmitCnt(problems.getProblemSubmitCnt()+1);
        if("Accept".equals(submissions.getSubmissionJudgeResult())){
            problems.setProblemAcceptCnt(problems.getProblemAcceptCnt()+1);
        }


        problemsService.saveOrUpdate(problems);


        //保存比赛提交记录
        ContestSubmissions contestSubmissions=new ContestSubmissions();
        contestSubmissions.setContestId(contestJudgeDto.getContestId());
        contestSubmissions.setSubmissionId(submissions.getSubmissionId());
        contestSubmissionsService.save(contestSubmissions);


        return Result.succ(MapUtil.builder()
                .put("uid",contestJudgeDto.getUid())
                .put("error",judgeResult.getError())
                .put("submissionJudgeResult",judgeResult.getSubmissionJudgeResult())
                .put("submissionUsedMemory",judgeResult.getSubmissionUsedMemory())
                .put("submissionUsedTime",judgeResult.getSubmissionUsedTime())
                .map()
        );
    }





    @GetMapping("/contest/statusList")
    public Result getStatusList(@RequestParam("pagenum") int pagenum,
                                @RequestParam("pagesize")int pagesize,
                                @RequestParam("contestId")int contestId,
                                @RequestParam("userid")int userid){


        List<ContestSubmissions> contestSubmissionsList=contestSubmissionsService
                .list(new QueryWrapper<ContestSubmissions>().eq("contestId",contestId));

        Collections.reverse(contestSubmissionsList);

        List<Submissions> submissionsList=new ArrayList<>();
        for(int i=0;i<contestSubmissionsList.size();i++){
            Submissions submissions=submissionsService.getById(contestSubmissionsList.get(i).getSubmissionId());
            if(submissions.getUid().equals(Integer.toString(userid))){
                submissionsList.add(submissions);
            }
        }//获取用户为id的在本次比赛的提交

        int total=submissionsList.size();

        List<Submissions> retList=new ArrayList<>();
        for(int i=Math.min(total-1,(pagenum-1)*pagesize);i<Math.min(total,pagenum*pagesize);i++){
            retList.add(submissionsList.get(i));
        }

        return Result.succ(MapUtil.builder()
                .put("statusList",retList)
                .put("total",total)
                .map());
    }
}
