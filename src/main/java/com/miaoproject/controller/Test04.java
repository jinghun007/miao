package com.miaoproject.controller;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Test04 {

    public static void main(String[] args) {
//        getIndex();
//        String ss="dddd";
//        ss.replace("d","2");
//        System.out.println(ss);
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void getIndex(){
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String str=scanner.nextLine();
            char[] strArr=str.toCharArray();
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<strArr.length;i++){
                stringBuffer.append(getNextStr(strArr[i]));
            }
            System.out.println(stringBuffer.toString());
        }
    }

    public static char getNextStr(char strChar){
        int temp=(int)strChar;
        switch (temp){
            case 90:temp=65;break;
            case 122:temp=97;break;
            default:temp+=1;
        }
        return (char)temp;
    }

}
