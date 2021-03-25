package com.demo.judger;


import com.demo.judger.data.ExecuteResult;
import com.demo.judger.data.JudgeResult;

public class CompileAndJudge {
    public static void main(String[] args) {
//        String code="#include<bits/stdc++.h>\n" +
//                "using namespace std;\n" +
//                "int main()\n" +
//                "{\n" +
//                "\twhile(1){}\n" +
//                "\treturn 0;\n" +
//                "}";
//        //code="#include<bits/stdc++.h>\nusing namespace std;\n\nint main(){\n\tint x,y;\n\tscanf(\"%d%d\",&x,&y);\n\tprintf(\"%d\\n%d\",x+y,x-y);\n\treturn 0;";
//        JudgeResult judgeResult=compileAndJudge(1,"C++",10,1,1000,code);
//        System.out.println(judgeResult);
    }

    //static final String filename="hello";
    public static JudgeResult compileAndJudge(Integer submissionId,String language, int timeLimit, int checkpoint_cnt, Integer problem_id,String submissionCode){

        //提交的代码生成.cpp 或.java文件

        language=language.toLowerCase();
        Integer id=submissionId%10;
        String filename=id.toString();

        if(language.contains("java")){//java

        }
        else if(language.contains("c")){//c++


            GenerateFileUtil.generate(submissionCode,"cpp",filename);

        }
        else{
            return null;
        }


        //编译程序
        ExecuteResult executeResult=Compile.startProgram(language,filename);
        System.out.println("编译成功");
        JudgeResult judgeResult=new JudgeResult();
        if(executeResult.getExitCode()==-1){
            judgeResult.setSubmissionJudgeResult("UnKnown Error");
        }
        else if(executeResult.getExitCode()==1){//编译错误
            judgeResult.setSubmissionJudgeResult("Compile Error");
            judgeResult.setError(executeResult.getExecuteOut());
        }
        else{//编译成功

            judgeResult= Judge.judge(filename,timeLimit,checkpoint_cnt,problem_id);


        }

        return judgeResult;
    }
}
