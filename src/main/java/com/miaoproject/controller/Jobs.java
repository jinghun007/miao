package com.miaoproject.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Jobs {
    @Scheduled(cron = "0/1 * * * * ?")
    public void sss(){
//        System.out.println("dddddd");
    }
}
