package com.demo.judger;


import com.demo.judger.data.ExecuteResult;

public class Compile {

    private static final String Cplusplus="g++ -O2 -s -Wall -std=c++11 -o {filename}.exe {filename}.cpp -lm";
    private static final String CplusplusFile_win="submitFile/cplusplus/";


    public static void main(String[] args){
        startProgram("C++","hello");
    }

    /*
    * 语言，文件名；
    * 返回：编译结果{返回值,编译信息或编译成功的信息};null不匹配当前系统
    * */
    public static ExecuteResult startProgram(String language, String filename){
        if(OSjudge.isOSWin()) {
            ExecuteResult executeResult=startCplusplus_win(filename);
            return executeResult;
        }
        else if(OSjudge.isOSLinux())
        {
            ExecuteResult executeResult=startCplusplus_linux(filename);
            return executeResult;
        }
        return null;
    }

    private static ExecuteResult startCplusplus_win(String filename) {
        String command=Cplusplus;
        String file=CplusplusFile_win+filename;


        command=command.replace("{filename}",file);
        command="cmd.exe /C "+command;//编译命令
        //System.out.println(command);

        ExecuteResult executeResult= new LocalCommandExecutorImpl().executeCommandOnlyOut(command);
        System.out.println(executeResult);

        return executeResult;
    }

    private static ExecuteResult startCplusplus_linux(String filename) {
        String command=Cplusplus;
        String file=CplusplusFile_win+filename;


        command=command.replace("{filename}",file);
        //command="cmd.exe /C "+command;//编译命令
        //System.out.println(command);

        ExecuteResult executeResult= new LocalCommandExecutorImpl().executeCommandOnlyOut(command);
        System.out.println(executeResult);

        return executeResult;
    }

}
