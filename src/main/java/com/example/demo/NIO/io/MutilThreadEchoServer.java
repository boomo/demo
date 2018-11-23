package com.example.demo.NIO.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MutilThreadEchoServer {
/*服务器 容易 IO 等待，拖慢服务器的处理速度*/


    //线程池
    private static ExecutorService exec = Executors.newCachedThreadPool();

    /*处理客户端消息的线程类.一个线程处理一个客户端的请求*/
    static class HandleMessag implements Runnable{
        Socket clientSocket;
        public HandleMessag(Socket socket){
            clientSocket = socket;
        }
        @Override
        public void run() {

            /*读*/
            BufferedReader is = null;
            /*写*/
            PrintWriter os = null;

            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                os = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);

                long b = System.currentTimeMillis();
                String inputLine = null;
                while ((inputLine  = is.readLine()) != null){
                    /*println 方法会在待输出文本追加 分隔符，然后调用flush()自动刷新
                    * 如果使用print方法，需要再添加 \n 符后，再调用flush()
                    * */
                    os.println(inputLine);
//                    os.flush();
                }
                long e = System.currentTimeMillis();
                System.out.println("spend :" + (e-b) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null){
                    os.close();
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        ServerSocket echoServer = null;
        Socket clientSocket  = null;
        try {
            /*创建一个服务端socket，监听8000端口*/
            echoServer = new ServerSocket(8000);
            System.out.println("EchoServer is running");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                /*接收客户端的请求*/
                clientSocket = echoServer.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
                /*使用线程池处理客户端请求*/
                exec.execute(new HandleMessag(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
