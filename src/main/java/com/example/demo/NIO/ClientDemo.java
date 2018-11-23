package com.example.demo.NIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientDemo {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        for (int j = 0;j<3;j++){
            exec.execute(new Client());
        }
    }

    static class Client implements Runnable{
        private Selector selector;
        private SocketChannel channel;
        public Client(){
            try {
                selector = SelectorProvider.provider().openSelector();
                channel = SocketChannel.open();
                channel.configureBlocking(false);

                channel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8000));
                channel.register(selector, SelectionKey.OP_CONNECT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {

            for (;;){
                if (!selector.isOpen())return;

                try {
                    selector.select();

                    Iterator<SelectionKey> i = selector.selectedKeys().iterator();

                    while (i.hasNext()){

                        SelectionKey sk = i.next();
                        i.remove();

                        if (sk.isConnectable()){
                            connection(sk);
                        }else if (sk.isReadable()){
                            toRead(sk);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void connection(SelectionKey sk) throws IOException {
            SocketChannel socketChannel = (SocketChannel) sk.channel();
            if (socketChannel.isConnectionPending()){
                socketChannel.finishConnect();
            }

            socketChannel.configureBlocking(false);
            ByteBuffer bb = ByteBuffer.wrap(new String("hu\r\n").getBytes());
            socketChannel.write(bb);

            channel.register(selector, SelectionKey.OP_READ);
        }

        private void toRead(SelectionKey sk) throws IOException {

            SocketChannel socketChannel = (SocketChannel) sk.channel();
            ByteBuffer bb = ByteBuffer.allocate(100);
            socketChannel.read(bb);

            byte[] data = bb.array();
            String dataStr = new  String(data).trim();
            System.out.println("客户端收到：" + dataStr);
            channel.close();
            sk.selector().close();

        }
    }
}
