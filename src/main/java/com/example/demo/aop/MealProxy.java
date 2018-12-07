package com.example.demo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*进食代理*/
public class MealProxy implements InvocationHandler {

    private Object proxyed;

    public MealProxy(Object proxyed){
        this.proxyed = proxyed;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        System.out.println("make a meal");
        Object result = method.invoke(proxyed, args);
        return result;
    }
}
