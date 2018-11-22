package com.example.demo.concurrent.future;

import java.util.concurrent.Callable;

public class RealData implements Callable {

    private String content;

    public RealData(String content){
        this.content = content;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<5;i++){
            sb.append(content);
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
