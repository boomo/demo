package com.example.demo.NIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static Map<Socket, Long> time_stat = new HashMap<>(10240);
    private Selector selector = null;
    private ExecutorService tp = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        new Server().startServer();
    }
    public void startServer(){
        try {
            //获取一个Selector
            selector = SelectorProvider.provider().openSelector();

            /*获得服务端的SocketChannel实例*/
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
//            InetSocketAddress isa = new InetSocketAddress(8000);
            ssc.socket().bind(isa);

            /*服务端channel注册到selector上*/
            SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

            for (;;){
                /*阻塞方法，一旦有数据就返回SelectionKey*/
                selector.select();
                Set readyKeys = selector.selectedKeys();
                Iterator i = readyKeys.iterator();
                long e = 0;
                while (i.hasNext()){
                    SelectionKey sk = (SelectionKey) i.next();
                    i.remove();

                    if(sk.isAcceptable()){
                        doAccept(sk);
                    }
                    else if (sk.isValid() && sk.isReadable()){
                        /*sk获知与客户端对应的通道channel可读*/
                            if (!time_stat.containsKey(((SocketChannel)sk.channel()).socket()))
                                time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());

                        doRead(sk);
                    }
                    else if (sk.isValid() && sk.isWritable()){
                        doWrite(sk);
                        e = System.currentTimeMillis();
                        long b = time_stat.remove(((SocketChannel)sk.channel()).socket());
                        System.out.println("spend:" + (e-b) + "ms");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;

        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);
            /*客户端channel，注册感兴趣的事件 为 “读”*/
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);

            /*创建一个echoClient实例，并将其附加到clinetKey上，后续可以通过这个SelectionKey共享这个echoClient对象实例*/
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk) {
        /*y由sk得到客户端的SocketChannel*/
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        try {
            /*读数据到缓存bb中*/
            len = channel.read(bb);
            if (len < 0){
                //ToDO:disconnect(sk)
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO:disconnect(sk);
            return;
        }
        /*重置缓存区*/
        bb.flip();

        /*派发一个HandleMsg线程去处理要读的数据*/
        tp.execute(new HandleMsg(sk, bb));

    }
    class HandleMsg implements Runnable{

        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey key, ByteBuffer bb){
            this.sk = key;
            this.bb = bb;
        }
        @Override
        public void run() {

            /*通过SelectionKey取到附件  echoClient 实例对象*/
            /*可以在这里对取到的数据进行一些业务处理...*/
            EchoClient echoClient = (EchoClient) sk.attachment();
            echoClient.enqueue(bb);

            /*对这个SelectionKey重新注册感兴趣的事件：读+写*/
            /*与客户端对应的channel可写时*/
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        }
    }
    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq  = echoClient.getOutputQueue();

        System.out.println("size = "+ outq.size());
        ByteBuffer bb = outq.getLast();

        try {
            int len = channel.write(bb);
            if (len == -1){
                //TODO: disconnect(sk);
                return;
            }

            if (bb.remaining() == 0){
                outq.removeLast();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: disconnect(sk);
        }

        if (outq.size() == 0){
            /*写完之后，从SelectionKey中移除写事件。否则 如果Channel准备好写了，但又无数据可写，显然是不合理的*/
            sk.interestOps(SelectionKey.OP_READ);
        }

    }

    class EchoClient{
        private LinkedList<ByteBuffer> outq;

        EchoClient(){
            outq = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutputQueue(){
            return outq;
        }

        public void enqueue(ByteBuffer bb){
            outq.addFirst(bb);
        }
    }


}
