package com.demo.judger;


import lombok.Data;

@Data
public class Monitor extends Thread{
    private Integer timeout;
    private Process process;

    public Monitor(Process process, Integer timeout) {
        this.process=process;
        this.timeout = timeout;
        System.out.println("开始检测");
    }

    @Override
    public void run() {
        try{
            Thread.sleep(timeout+200);
            if(process!=null) {
                process.destroy();
            }
            //System.out.println("TLE!!!!!!!!!!!!!!!!!!!!!!");
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
