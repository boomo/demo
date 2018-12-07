package com.example.demo.aop;

import java.lang.reflect.Proxy;

public class OneWorkDay {

    public static void main(String[] args) {
        Programmer programmer = new Programmer("boomo");

        System.out.println(programmer.getClass());

        IPeople people = (IPeople) Proxy.newProxyInstance( programmer.getClass().getClassLoader(),
                programmer.getClass().getInterfaces(),
                new MealProxy(programmer));

//        people.work();

        IPeople clear = (IPeople) new MyProxy(people).bind();
        clear.meditating();
    }
}
