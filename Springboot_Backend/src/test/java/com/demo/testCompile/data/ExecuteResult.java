package com.demo.testCompile.data;

import com.demo.testCompile.StringClean;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExecuteResult {
    /*
    * @param 1:编译错误;0:正常;-1:未知错误
    * */
    private int exitCode;
    private String executeOut;
    private int executeTime;

    public ExecuteResult(int exitCode, String executeOut) {
        executeOut= StringClean.clean(executeOut);

        this.executeTime=0;
        this.exitCode = exitCode;
        this.executeOut = executeOut;
    }

    public ExecuteResult(int exitCode, String executeOut,int executeTime) {
       executeOut=StringClean.clean(executeOut);

        this.exitCode = exitCode;
        this.executeOut = executeOut;
        this.executeTime=executeTime;

    }


}
