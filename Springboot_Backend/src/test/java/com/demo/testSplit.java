package com.demo;

public class testSplit {

    public static void main(String[] args) {
        String s="1,2,4,100";
        String[] a=s.split(",");
        System.out.println(a[0]);
    }
}
