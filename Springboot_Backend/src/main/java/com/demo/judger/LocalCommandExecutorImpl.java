package com.demo.judger;



import com.demo.judger.data.ExecuteResult;
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


    @Override
    public ExecuteResult executeCommandInAndOut(String command,int timeout, String filepath) {
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

            System.out.println("进程启动");
            new Thread(new Monitor(process,timeout)).start();

            if(process==null) {
                return new ExecuteResult(-2, "Time Limit Exceed");
            }

            long startTime=System.currentTimeMillis();

            // close process's output stream.
            pOut=p.getOutputStream();
            InputStream inputStream=new FileInputStream(filepath);
            inputGobbler=new StreamIn(pOut,"INPUT",inputStream);
            inputGobbler.start();
            int in_exitcode=0;
            in_exitcode=process.waitFor();

            System.out.println("输入结束");

            if(in_exitcode!=0)return new ExecuteResult(in_exitcode,null);//错误


            pIn = process.getInputStream();
            outputGobbler = new StreamOut(pIn, "OUTPUT");
            outputGobbler.start();

            pErr = process.getErrorStream();
            errorGobbler = new StreamOut(pErr, "ERROR");
            errorGobbler.start();


            int exitCode = 0;


            exitCode=process.waitFor();


            long endTime=System.currentTimeMillis();

            System.out.println("程序执行结束");

            if(exitCode==1) {
                return new ExecuteResult(exitCode, errorGobbler.getContent());//错误
            }
            else {
                int time=(int)(endTime-startTime);
                System.out.println(startTime);
                System.out.println(endTime);
                if(time>timeout) {
                    exitCode=-2;
                }
                return new ExecuteResult(exitCode, outputGobbler.getContent(),time);
            }//返回程序输出结果

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

    @Override
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
