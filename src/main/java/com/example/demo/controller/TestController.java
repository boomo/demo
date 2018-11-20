package com.example.demo.controller;


import com.example.demo.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private User user;

    @Value("demo.date")
    private String date;

    @Value("${demo.date}")
    private String date2;

    @RequestMapping("/user")
    public User user(){
        return user;
    }

    @RequestMapping("/date")
    public String getDate(){
        return  date;
    }

    @RequestMapping("/date2")
    public String date2(){
        String s = new Gson().toJson(user);
        System.out.println(s);
        return date2;
    }


}
