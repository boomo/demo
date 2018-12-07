package com.example.demo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CleanProxy implements InvocationHandler {

    private Object proxyed;

    public CleanProxy(Object proxyed){
        this.proxyed = proxyed;
    }

    public Object bind(){
        System.out.println("----------------------");
        System.out.println(this.proxyed.getClass());
        System.out.println(this.proxyed.getClass().getClassLoader());
        System.out.println(this.proxyed.getClass().getInterfaces());
        return Proxy.newProxyInstance(this.proxyed.getClass().getClassLoader(),
                this.proxyed.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        Object ret = method.invoke(proxyed, args);
        System.out.println("wash dishes");
        return ret;
    }
}
