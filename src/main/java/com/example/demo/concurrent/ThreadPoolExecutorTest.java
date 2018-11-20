package com.example.demo.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ThreadPoolExecutorTest {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorTest.class);
    public static void main(String[] args) {
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(6);
        Executors.newSingleThreadExecutor();
        /*
     ThreadPoolExecutor(int corePoolSize, 线程池中线程数量
                      int maximumPoolSize, 最大线程数量
                      long keepAliveTime,   线程数量超过corePoolSize时，多余线程的存活时间
                      TimeUnit unit,    keepAliveTime单位
                      BlockingQueue<Runnable> workQueue,  任务队列，被提交但尚未执行的任务
                      ThreadFactory threadFactory,  线程工厂，用来创建线程
                      RejectedExecutionHandler handler) 拒绝策略，任务太多来不及处理时的拒绝方法
        * */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        System.out.println("create thread " + thread);
                        return thread;
                    }
                },
                new RejectedExecutionHandler() {

                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        logger.info("discard {}", ((MyTask)r).name);
                    }
                }){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                logger.info("准备执行线程{}" , ((MyTask)r).name);
            }

            @Override
            protected void terminated() {
                super.terminated();
                logger.info("exit");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                logger.info("线程{}执行完毕" , ((MyTask)r).name);
            }
        };

        for (int i = 1 ;i <= 20; i++){
            executor.execute(new MyTask("task-" + i));
        }

        /*安全关闭线程池*/
        executor.shutdown();
    }

    public static class MyTask implements Runnable{
        private String name;
        public MyTask(String name){
            this.name = name;
        }
        @Override
        public void run() {
            logger.info("正在执行线程，ID={},name={}",Thread.currentThread().getId(),name);
        }
    }
}
