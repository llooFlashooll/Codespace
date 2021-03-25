package com.demo.testCompile;

import com.demo.testCompile.data.ExecuteResult;
import com.demo.testCompile.data.JudgeResult;

public class CompileAndJudge {
    public static void main(String[] args) {

    }

    static final String filename="hello";
    public static JudgeResult CJ(String language,int timeLimit,int checkpoint_cnt,Integer problem_id){

        //编译程序
        ExecuteResult executeResult=Compile.startProgram(language,filename);

        JudgeResult judgeResult=new JudgeResult();
        if(executeResult.getExitCode()==-1){
            judgeResult.setSubmissionJudgeResult("UnKnown Error");
        }
        else if(executeResult.getExitCode()==1){//编译错误
            judgeResult.setSubmissionJudgeResult("Compile Error");
            judgeResult.setError(executeResult.getExecuteOut());
        }
        else{//编译成功

            judgeResult=Judge.judge(filename,timeLimit,checkpoint_cnt,problem_id);


        }

        return judgeResult;
    }
}
