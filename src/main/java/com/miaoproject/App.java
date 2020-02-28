package com.miaoproject;

import com.miaoproject.dao.UserInfoMapper;
import com.miaoproject.dataobject.UserInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Hello world!
 *把app启动类当成一个自动化可以支持配置的bean，并且开启整个一个工程类的基于spring的一个自动化配置
 */
@SpringBootApplication(scanBasePackages = "com.miaoproject")
@RestController
@MapperScan("com.miaoproject.dao")
@EnableScheduling
public class App {

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        HashMap map=new HashMap();
        map.put()
        SpringApplication.run(App.class,args);
    }
}
