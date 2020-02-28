package com.miaoproject.controller;


public class Test03 {

    private static final int MONEY=100;
    //公鸡价格
    private static final int COCK=5;
    //母鸡价格
    private static final int HRN=3;
    //一钱可以买小鸡个数
    private static final int CHICKEN_NUM=3;

    public static void main(String[] args) {
        getMainNum();
    }

    public static void getMainNum(){
        int total=0;
        int cockTotal=MONEY/COCK;//最多公鸡个数
        for(int i=0;i<=cockTotal;i++){
            int remainderMoneryHen=MONEY-COCK*i;
            int henTotal=remainderMoneryHen/HRN;//最多母鸡个数
            for(int j=0;j<=henTotal;j++){
                int remainderMonery=remainderMoneryHen-HRN*j;
                int chickenNum=remainderMonery*CHICKEN_NUM;
                if(chickenNum+i+j>=100)
                    total++;
            }
        }
        System.out.println(total);
    }

}
