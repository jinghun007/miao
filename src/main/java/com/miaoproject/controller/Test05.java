package com.miaoproject.controller;

import java.util.*;

public class Test05 {

    public static void main(String[] args) {
        getIndex();
    }

    public static void getIndex(){
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String str=scanner.nextLine();
            String[] strArr=str.split(",");
            //保护最后取值，要求必须两个index以上
            if(strArr.length<2){
                return ;
            }
            int[] intArr=new int[strArr.length];
            for(int i=0;i<strArr.length;i++){
                intArr[i]=Integer.valueOf(strArr[i]);
            }
            System.out.println(getNum(intArr));
        }
    }

    public static String getNum(int[] intArr){
        List<Integer> resultList = new ArrayList<>(intArr.length);
        for (Integer s : intArr) {
            resultList.add(s);
        }
        resultList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1<=o2?1:-1;
            }
        });
        return resultList.get(0)+","+resultList.get(1);
    }

}
