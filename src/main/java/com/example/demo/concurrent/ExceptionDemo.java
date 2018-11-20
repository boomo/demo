package com.example.demo.concurrent;

import com.google.common.collect.Maps;
import com.sun.tools.hat.internal.model.HackJavaValue;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.util.StringUtil;

import java.util.HashMap;
import java.util.concurrent.*;

public class ExceptionDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        for (int i = 0; i < 5 ;i++){
            /*不会打印异常堆栈信息*/
//            executor.submit(new Task(100,i));

            /*打印异常堆栈*/
            executor.execute(new Task(100, i));

//            Future future = executor.submit(new Task(100, i));
        }

        executor.shutdown();
    }
    public static class Task implements Runnable{
        private int a;
        private int b;

        public Task(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            System.out.println(a/b);
        }
    }
}
