package com.example.demo.config;

import com.example.demo.entity.User;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
    private Logger logger = LoggerFactory.getLogger(LogConfig.class);
    private Gson gson = new Gson();

    @Bean
    public User getUser(){
        User user = new User();
        user.setUsername("hly");
        user.setPassword("hly1234");
        logger.info("user={}",gson.toJson(user));
        return user;
    }
}
