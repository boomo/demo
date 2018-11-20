package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.model.SpecialValue;
import com.example.demo.service.SpecialValueService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private SpecialValueService service;

    @Autowired
    private User user;
    @Test
    public void contextLoads() {
      /*  List<SpecialValue> list = service.getByTaskId(56);
        System.out.println(list);
        System.out.println();*/

        String s = new Gson().toJson(user);
        System.out.println(s);
    }



}
