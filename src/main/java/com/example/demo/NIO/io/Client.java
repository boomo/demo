package com.example.demo.NIO.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class Client {

    static class EchoClient implements Runnable{
        private long sleep_time = 1000 * 1000 * 1000;
        @Override
        public void run() {
            Socket clientSocket = null;
            PrintWriter os = null;
            BufferedReader is = null;

            /*创建一个客户端socket, 并连接到服务器的8000端口*/
            clientSocket = new Socket();
            try {
                clientSocket.connect(new InetSocketAddress("localhost", 8000));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os = new PrintWriter(clientSocket.getOutputStream(),true);
                os.print("H");
                LockSupport.parkNanos(sleep_time);/*模拟IO，造成服务端 IO 等待，拖慢服务器的处理速度*/
                os.print("e");
                LockSupport.parkNanos(sleep_time);
                os.print("ll");
                LockSupport.parkNanos(sleep_time);
                os.print("o");
                os.print("\n");
                os.flush();

                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("from server:" + is.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

                if (os != null)os.close();
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (clientSocket != null){
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private static ExecutorService exec = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        for (int i = 0 ;i< 5;i++){
            exec.execute(new EchoClient());
        }
        exec.shutdown();
    }
}
