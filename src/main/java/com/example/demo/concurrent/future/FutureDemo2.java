package com.example.demo.concurrent.future;

import java.util.concurrent.*;

public class FutureDemo2 {

    /*使用Future + Callable 方式*/

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        Future<String> future =  exec.submit(new RealData("hly"));
        System.out.println("main Thread is running");
        try {
            String ret = future.get();
            System.out.println("the read data : " + ret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        exec.shutdown();
        System.out.println("main thread is finished");
    }
}
