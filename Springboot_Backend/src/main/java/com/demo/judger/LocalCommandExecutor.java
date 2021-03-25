package com.demo.judger;


import com.demo.judger.data.ExecuteResult;

public interface LocalCommandExecutor {
    ExecuteResult executeCommandOnlyOut(String command);
    ExecuteResult executeCommandInAndOut(String command,int timeout,String filepath);

}
