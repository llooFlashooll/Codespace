package com.demo.controller;


import cn.hutool.core.map.MapUtil;
import com.demo.common.lang.Result;
import com.demo.common.lang.dto.JudgeDto;
import com.demo.entity.Problems;
import com.demo.entity.Submissions;
import com.demo.judger.CompileAndJudge;
import com.demo.judger.data.JudgeResult;
import com.demo.service.ProblemsService;
import com.demo.service.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problem")
public class JudgeController {

    @Autowired
    SubmissionsService submissionsService;

    @Autowired
    ProblemsService problemsService;

    //@RequiresAuthentication//登录后才能提交
    @PostMapping("/judge")
    public Result judge(@Validated @RequestBody JudgeDto judgeDto){

//        System.out.println(judgeDto.getSubmissionCode());
//        return Result.succ(null);


        if(judgeDto.getSubmissionCode().contains("ystem")){


            return Result.succ(MapUtil.builder()
                    .put("submissionJudgeResult","code is killed")
                    .put("error","")
                    .map()
            );
        }
        Problems problems=problemsService.getById(judgeDto.getProblem_id());
        judgeDto.setCheckpoint_cnt(problems.getProblemCheckPointCnt());
        judgeDto.setTimeLimit(problems.getProblemTimeLimit());

        System.out.println(judgeDto);

        Integer submission_cnt=submissionsService.count()+1;
        Submissions submissions=new Submissions();

        {


            submissions.setSubmissionId(submission_cnt);

            submissions.setLanguageId(judgeDto.getLanguageId());
            submissions.setProblemId(judgeDto.getProblem_id());
           submissions.setSubmissionCode(judgeDto.getSubmissionCode());
            submissions.setSubmissionJudgeResult("judging");


            // System.out.println(submissions);
            submissions.setUid(judgeDto.getUid());


        }
        submissionsService.save(submissions);

        JudgeResult judgeResult= CompileAndJudge.compileAndJudge(
                submission_cnt,
                judgeDto.getLanguage(),
                judgeDto.getTimeLimit(),
                judgeDto.getCheckpoint_cnt(),
                judgeDto.getProblem_id(),
                judgeDto.getSubmissionCode()
        );



        {



            submissions.setSubmissionJudgeResult(judgeResult.getSubmissionJudgeResult());

            submissions.setSubmissionUsedMemory(judgeResult.getSubmissionUsedMemory());
            submissions.setSubmissionUsedTime(judgeResult.getSubmissionUsedTime());
           // System.out.println(submissions);


           // System.out.println(submissions);

        }

        if(judgeResult.getSubmissionJudgeResult().equals("Compile Error")){
            submissions.setSubmissionCode(judgeDto.getSubmissionCode()+"\n\n\n"+judgeResult.getError());
            System.out.println("CE!!!!");
        }
        submissionsService.saveOrUpdate(submissions);

        problems.setProblemSubmitCnt(problems.getProblemSubmitCnt()+1);
        if(submissions.getSubmissionJudgeResult().equals("Accept")){
            problems.setProblemAcceptCnt(problems.getProblemAcceptCnt()+1);
        }


        problemsService.saveOrUpdate(problems);


        return Result.succ(MapUtil.builder()
                .put("uid",judgeDto.getUid())
                .put("error",judgeResult.getError())
                .put("submissionJudgeResult",judgeResult.getSubmissionJudgeResult())
                .put("submissionUsedMemory",judgeResult.getSubmissionUsedMemory())
                .put("submissionUsedTime",judgeResult.getSubmissionUsedTime())
                .map()
        );
    }
}
