package com.example.demo.aop;

import java.lang.reflect.Proxy;

public class CommonDay {
    public static void main(String[] args) {
        Programmer programmer = new Programmer("boomo");

        Nurse nurse = new Nurse();
        IPeople people = (IPeople) new MyProxy(programmer, nurse).bind();
        people.sport();
    }
}
