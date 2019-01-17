package com.example.demo.config;

import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class LogConfig {
    private Logger logger = LoggerFactory.getLogger(LogConfig.class);
    private Gson gson = new Gson();
    public static Integer page;


    @Bean(name = "page2")
    public Integer initPage2(){
        System.out.println("initPage2 +-+-+-+-+-+-+ " + page);
        System.out.println("initPage2");
        page = 200;
        return page;
    }

    @Bean(name = "page1")
    public Integer initPage1(){
        System.out.println("initPage1");
        page = 100;
        return page;
    }

    @PostConstruct
    public void init(){
        System.out.println("init +-+-+-+-+-+-+ " + page);
        System.out.println("PostConstruct");
        page = 123;
    }

    @Bean(name = "myUser")
    public User getUser(){
        User user = new User();
        user.setUsername("hly");
        user.setPassword("hly1234");
        logger.info("user={}",gson.toJson(user));
        return user;
    }

    @Bean
    public Student getStu(){
        Student student = new Student();
        student.setSId(1000);
        student.setSName("hly");
        return student;
    }


}
