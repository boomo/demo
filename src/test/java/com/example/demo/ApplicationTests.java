package com.example.demo;

import com.example.demo.config.LogConfig;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.service.SpecialValueService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


    @Resource(name = "myUser")
    private User user;

    @Autowired
    private Student student;

    /*变量先于方法初始化, @Qualifier 多个同类型bean，指定某一个bean*/
    @Autowired
    @Qualifier(value = "page2")
    Integer page;

    @Autowired
    public void getPage(){
        System.out.println("get Page  = "  + page  + "\t LogConfig.page = " + LogConfig.page);
        page = LogConfig.page;
    }

//    @Autowired
//    public void getPage(@Qualifier(value = "page1") Integer page){
//        this.page = page;
//    }

    @Test
    public void contextLoads() {
        System.out.println("user = "  + user);
        System.out.println("student = " + student);

        System.out.println("page = " + page + "\tLogConfig.page = " +  LogConfig.page);
    }



}
