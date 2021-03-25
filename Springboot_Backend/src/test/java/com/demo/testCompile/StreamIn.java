package com.demo.testCompile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class StreamIn extends Thread{
    private static Logger logger = LoggerFactory.getLogger(StreamOut.class);
    private OutputStream outputStream;
    private InputStream inputStream;
    private String streamType;
    private volatile boolean isStopped = false;

    /*
    * 从文件流inputStream读数据输出到outputStream
    * */
    public StreamIn(final OutputStream outputStream, final String streamType,final InputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream=inputStream;
        this.streamType = streamType;
        this.isStopped = false;
    }

    @Override
    public void run() {
        try {
            // 默认编码为UTF-8，这里设置编码为GBK，因为WIN的编码为GBK
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"GBK");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);

            InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"GBK");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();

            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException ex) {
            logger.trace("Failed to successfully consume and display the input stream of type " + streamType + ".", ex);
        } finally {
            this.isStopped = true;
            synchronized (this) {
                notify();
            }
        }
    }



}
