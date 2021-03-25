package com.demo.testCompile;

import com.demo.testCompile.data.ExecuteResult;
import com.demo.testCompile.data.JudgeResult;

import java.io.*;

public class Judge {

    private static final String CplusplusFile_win="submitFile/cplusplus/";

    public static void main(String[] args) {
        JudgeResult judgeResult=judge("hello",10,1,1000);
        System.out.println(judgeResult);
    }

    /*
    * @param 文件名，实现，评测点个数，问题的id；
    * @param 评测结果
    * */
    public static JudgeResult judge(String filename,int timeLimit,int checkpoint_cnt,Integer problem_id) {

        String command=".\\"+CplusplusFile_win+filename+".exe";
        System.out.println(command);
        LocalCommandExecutor localCommandExecutor=new LocalCommandExecutorImpl();

        String submissionJudgeResult="";
        int submissionUsedTime=0;
        for(int i=1;i<=checkpoint_cnt;i++){

            /*
            * 运行程序，得到程序输出
            * */
            Integer checkpoint_id=i;
            String in_filepath="problem/"+problem_id.toString()+"/"+checkpoint_id.toString()+".in";
            ExecuteResult executeResult=localCommandExecutor.executeCommandInAndOut(command,timeLimit,in_filepath);

            if(executeResult.getExitCode()==-1){
                submissionJudgeResult="Runtime Error";
                break;
            }
            else if(executeResult.getExitCode()==1){
                submissionJudgeResult="Runtime Error";
                break;
            }
            System.out.println(executeResult);

            /*
            * 比较结果是否相同
            * */


            try {
                StringBuilder buf=new StringBuilder();
                String out_filepath="problem/"+problem_id.toString()+"/"+checkpoint_id.toString()+".out";
                InputStream inputStream = new FileInputStream(out_filepath);
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                String line;
                while((line=bufferedReader.readLine())!=null){
                    line=StringClean.clean(line);
                    buf.append(line);
                }
//                System.out.println(buf.toString());
//                System.out.println(buf.toString().length());
//                System.out.println(executeResult.getExecuteOut());
//                System.out.println(executeResult.getExecuteOut().length());


                /*
                * 比较结果
                * */
                if(!buf.toString().equals(executeResult.getExecuteOut())){
                    submissionJudgeResult="Wrong Answer";
                    break;
                }
                submissionUsedTime=Math.max(submissionUsedTime,executeResult.getExecuteTime());

            }catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if(submissionUsedTime>timeLimit){
            submissionJudgeResult="Time Limit Exceed";
        }
        else if(submissionJudgeResult.equals("")){
            submissionJudgeResult="Accept";
        }

        JudgeResult judgeResult=new JudgeResult();
        judgeResult.setSubmissionUsedMemory(0);
        judgeResult.setSubmissionJudgeResult(submissionJudgeResult);
        judgeResult.setSubmissionUsedTime(submissionUsedTime);

        return judgeResult;
    }



}
