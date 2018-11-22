package com.example.demo.concurrent.future;

import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) {
        FutureTask<String> task = new FutureTask<String>(new RealData("test"));
        ExecutorService exec = Executors.newFixedThreadPool(2);
        exec.execute(task);

        System.out.println("begin ");
        System.out.println("ready");
        System.out.println("is ok?");
        try {
            String ret = task.get(10, TimeUnit.SECONDS);
            System.out.println("the return content : " + ret);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        exec.shutdown();
        System.out.println("finished");
    }
}
