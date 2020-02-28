package com.miaoproject.controller;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test07 {


    public static void main(String[] args) {
        test02();
    }
    public  static  void test02(){
        int s=6;
        int d=Math.abs(s);
        System.out.println(d);
    }
    public  static  void test01(){
        try {
            Thread t=new Thread(new ThreadTest());
//            t.setName("ddd");
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println("caught   "+t);
                }
            });
            t.start();
        } catch (Exception e) {
            System.out.println("dddddddddddddddddddddddddddddd");
        }
    }

}

class ThreadTest implements Runnable {

    @Override
    public void run() {
        System.out.println("ThreadTest.Runnable 运行");
        throw new RuntimeException();
    }
}
