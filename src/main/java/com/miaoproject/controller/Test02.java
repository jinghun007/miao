package com.miaoproject.controller;

import java.util.Scanner;

public class Test02 {

    private static final int NUM_DELE=3;

    public static void main(String[] args) {
        getIndex();
    }

    public static void getIndex(){
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            int num=scanner.nextInt();
            int remainder = 0;
            for (int i = 2; i <= num; ++i)
                remainder = (remainder+NUM_DELE)%i;
            System.out.println(remainder+1);
        }
    }

}
