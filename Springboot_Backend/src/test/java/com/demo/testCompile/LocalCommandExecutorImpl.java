package com.demo.testCompile;


import com.demo.testCompile.data.ExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LocalCommandExecutorImpl implements LocalCommandExecutor {

    static final Logger logger = LoggerFactory.getLogger(LocalCommandExecutorImpl.class);

    static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());


    /*
    * @param 指令，时限，输入文件的路径
    * */
    public ExecuteResult executeCommandInAndOut(String command, int timeout, String filepath) {


        ExecuteResult executeResult=executeCommandInAndOut(command,filepath);

        return executeResult;
    }


    public ExecuteResult executeCommandInAndOut(String command,String filepath) {
        Process process = null;
        InputStream pIn = null;
        InputStream pErr = null;
        OutputStream pOut=null;
        StreamOut outputGobbler = null;
        StreamOut errorGobbler = null;
        StreamIn inputGobbler=null;
        try {
            logger.info(command.toString());
            process = Runtime.getRuntime().exec(command);
            final Process p = process;

            // close process's output stream.
            pOut=p.getOutputStream();
            InputStream inputStream=new FileInputStream(filepath);
            inputGobbler=new StreamIn(pOut,"INPUT",inputStream);
            inputGobbler.start();
            int in_exitcode=process.waitFor();

            if(in_exitcode==1)return new ExecuteResult(1,null);//错误

            pIn = process.getInputStream();
            outputGobbler = new StreamOut(pIn, "OUTPUT");
            outputGobbler.start();

            pErr = process.getErrorStream();
            errorGobbler = new StreamOut(pErr, "ERROR");
            errorGobbler.start();

            int exitCode = process.waitFor();
            if(exitCode==1)return new ExecuteResult(exitCode,errorGobbler.getContent());//错误
            else return new ExecuteResult(exitCode, outputGobbler.getContent());//返回程序输出结果

        } catch (IOException ex) {
            String errorMessage = "The command [" + command + "] execute failed.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        }catch (InterruptedException ex) {
            String errorMessage = "The command [" + command + "] did not complete due to an interrupted error.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        } finally {

            if (pIn != null) {
                this.closeQuietly(pIn);
                if (outputGobbler != null && !outputGobbler.isInterrupted()) {
                    outputGobbler.interrupt();
                }
            }
            if (pErr != null) {
                this.closeQuietly(pErr);
                if (errorGobbler != null && !errorGobbler.isInterrupted()) {
                    errorGobbler.interrupt();
                }
            }
            if (process != null) {
                process.destroy();
            }
            if(pOut!=null)
            {
                this.closeQuietly(pOut);
                if (inputGobbler != null && !inputGobbler.isInterrupted()) {
                    inputGobbler.interrupt();
                }
            }
        }

    }

    public ExecuteResult executeCommandOnlyOut(String command) {
        Process process = null;
        InputStream pIn = null;
        InputStream pErr = null;
        StreamOut outputGobbler = null;
        StreamOut errorGobbler = null;
        try {
            logger.info(command.toString());
            process = Runtime.getRuntime().exec(command);
            final Process p = process;

            // close process's output stream.
            p.getOutputStream().close();

            pIn = process.getInputStream();
            outputGobbler = new StreamOut(pIn, "OUTPUT");
            outputGobbler.start();

            pErr = process.getErrorStream();
            errorGobbler = new StreamOut(pErr, "ERROR");
            errorGobbler.start();




            int exitCode = process.waitFor();
            if(exitCode==1)return new ExecuteResult(exitCode,errorGobbler.getContent());//编译错误
            else return new ExecuteResult(exitCode, outputGobbler.getContent());//返回结果

        } catch (IOException ex) {
            String errorMessage = "The command [" + command + "] execute failed.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        }catch (InterruptedException ex) {
            String errorMessage = "The command [" + command + "] did not complete due to an interrupted error.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        } finally {

            if (pIn != null) {
                this.closeQuietly(pIn);
                if (outputGobbler != null && !outputGobbler.isInterrupted()) {
                    outputGobbler.interrupt();
                }
            }
            if (pErr != null) {
                this.closeQuietly(pErr);
                if (errorGobbler != null && !errorGobbler.isInterrupted()) {
                    errorGobbler.interrupt();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    private void closeQuietly(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (IOException e) {
            logger.error("exception", e);
        }
    }
}
