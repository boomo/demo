package com.example.demo.controller;

import com.example.demo.model.SpecialValue;
import com.example.demo.service.SpecialValueService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpecialValueController {

    @Autowired
    private SpecialValueService SpecialValueService;
    @RequestMapping("/show")
    public List<SpecialValue> show(){
        List<SpecialValue> list = SpecialValueService.getByTaskId(56);
        System.out.println(list);
        return list;
    }
}
