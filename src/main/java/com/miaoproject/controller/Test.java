package com.miaoproject.controller;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        isAliquotOfNine();
    }

    //是否被9整除
    public static void isAliquotOfNine(){
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            int num=scanner.nextInt();
            int sum=getNumSum(num);
            boolean flag=sum%9==0?true:false;
            System.out.println(flag);
        }
    }

    //计算整数的各个数之和
    public static int getNumSum(int num){
        return getNumSum(num,0);
    }
    //计算整数的各个数之和
    public static int getNumSum(int num,int sum){
        int integerNum=num/10;
        int remainder=num%10;
        sum+=remainder;
        if(0!=integerNum){
            return getNumSum(integerNum,sum);
        }
        return sum;
    }

}
