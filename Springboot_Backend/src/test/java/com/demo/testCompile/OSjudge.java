package com.demo.testCompile;

import java.util.Properties;

public class OSjudge {

    public static void main(String[] args) {
        System.out.println(isOSWin());
    }

    public static boolean isOSLinux(){
        Properties properties=System.getProperties();


        String os=properties.getProperty("os.name");
       // System.out.println(os);
        return os != null && os.toLowerCase().contains("linux");
    }

    public static boolean isOSWin(){
        Properties properties=System.getProperties();


        String os=properties.getProperty("os.name");
        // System.out.println(os);
        return os != null && os.toLowerCase().contains("win");
    }
}
