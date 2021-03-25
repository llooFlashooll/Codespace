package com.demo.testCompile;

public class StringClean {

    public static void main(String[] args) {
        String a="5\n\r";
        System.out.println(a.length());
        a=clean(a);
        System.out.println(a.length());
    }

    public static String clean(String str){
        str=str.replaceAll("\n","");
        str=str.replaceAll("\r","");

        return str;
    }
}
