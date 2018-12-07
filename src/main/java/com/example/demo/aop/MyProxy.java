package com.example.demo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy implements InvocationHandler {

    /*代理对象*/
    private Object proxy;
    /*被代理对象*/
    private Object proxyed;

    public MyProxy(Object proxyed){
        this.proxyed = proxyed;
    }

    public MyProxy(Object proxyed, Object proxy){
        this.proxyed = proxyed;
        this.proxy = proxy;
    }

    public Object bind(){
        return Proxy.newProxyInstance(this.proxyed.getClass().getClassLoader(),
                this.proxyed.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        /*method -- 连接点，使用反射 对调用的方法前后进行一些其他操作*/
        Class nClass  = this.proxy.getClass();
        Method make = nClass.getDeclaredMethod("start", null);
        make.invoke(this.proxy, null);

        Object ret = method.invoke(proxyed, args);

        Method clear = nClass.getDeclaredMethod("end", null);
        clear.invoke(this.proxy, null);
        return ret;
    }
}
