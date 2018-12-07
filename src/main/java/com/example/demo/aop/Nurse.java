package com.example.demo.aop;
/*
* 保姆类
* */
public class Nurse implements Servant{
    @Override
    public void start() {
        makeMeal();
    }

    @Override
    public void end() {
        washDishes();
    }

    public void makeMeal(){
        System.out.println("make meal");
    }
    
    public void washDishes(){
        System.out.println("wash dishes");
    }
}
