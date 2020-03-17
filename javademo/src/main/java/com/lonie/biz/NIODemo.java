package com.lonie.biz;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author huzeming Created time 2020/3/16 : 8:56 下午 Desc:
 */

public class NIODemo {

    static InetAddress address;

    static String host = "127.0.0.1";

    static {
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    static int port = 9000;

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("begin server");
                try {
                    NIODemo.startServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("end server");
            }
        }).start();

        //System.in.read();

        Thread.sleep(5000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("start client");
                    try {
                        client();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("end client");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.in.read();

    }

    public static void startServer() throws Exception {

        //开启selector
        Selector selector = Selector.open();
        selector.select(1000);

        //开启channel
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(port), 1000);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        //遍历key
        while (true) {
            int events = selector.select();
            if (events > 0) {
                HashSet<SelectionKey> keys = new HashSet<>(selector.keys());
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {

                    key = iterator.next();
                    //套接字接受操作的操作集位
                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        System.out.println("accept");
                        ServerSocketChannel channel1 = (ServerSocketChannel)key.channel();
                        SocketChannel socketChannel = channel1.accept();

                        if (socketChannel == null) {
                            System.out.println("md");
                            continue;
                        }

                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    //读取
                    if (key.isReadable()) {
                        System.out.println("read");
                        SocketChannel socketChannel = (SocketChannel)key.channel();
                        long start = System.currentTimeMillis();
                        String body = read(socketChannel);
                        System.out.println(body + "  文件操作了 " + (System.currentTimeMillis() - start) + " ms");
                        write(socketChannel, body + "  文件操作了 " + (System.currentTimeMillis() - start) + " ms");
                    }

                    iterator.remove();
                }
            }
        }
        //selector.close();
    }

    public static void client() throws IOException {
        for (int i = 0; i < 10; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SocketChannel socket = SocketChannel.open();
                        socket.configureBlocking(false);
                        Selector selector = Selector.open();
                        socket.register(selector, SelectionKey.OP_CONNECT);
                        socket.connect(new InetSocketAddress(host, port));

                        while (true) {
                            int events = selector.select();
                            if (events > 0) {
                                HashSet<SelectionKey> keys = new HashSet<>(selector.keys());
                                Iterator<SelectionKey> iterator = keys.iterator();
                                SelectionKey key;
                                while (iterator.hasNext()) {
                                    key = iterator.next();

                                    if (key.isConnectable()) {
                                        System.out.println("可连接");
                                        SocketChannel socketChannel = (SocketChannel)key.channel();
                                        if (socketChannel.isConnectionPending()) {
                                            socketChannel.finishConnect();
                                        }

                                        socketChannel.configureBlocking(false);
                                        socketChannel.register(selector, SelectionKey.OP_READ);
                                        socketChannel.write(
                                            ByteBuffer
                                                .wrap(
                                                    ("Hello this is " + Thread.currentThread().getName()).getBytes()));
                                    } else if (key.isReadable()) {
                                        SocketChannel sc = (SocketChannel)key.channel();
                                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                                        sc.read(buffer);
                                        buffer.flip();
                                        System.out.println("收到服务端的数据：" + new String(buffer.array()));
                                    }
                                    iterator.remove();

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
        ////写数据
        //write(socket, "this is my content " + 0);
        //
        ////读数据
        //read(socket);
        //}

    }

    private static void write(SocketChannel socket, String content) throws IOException {

        byte[] bytes = content.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);  //该ByteBuffer是非阻塞通道
        writeBuffer.put(bytes);
        writeBuffer.flip();
        socket.write(writeBuffer);
    }

    /**
     * 读数据
     */
    private static String read(SocketChannel socket) throws IOException {
        String result = null;
        //1.Channel获取数据到缓冲区
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int readBytes = socket.read(readBuffer);
        if (readBytes > 0) {
            readBuffer.flip(); //将缓冲区最大指标limit=position,将当前位置position=0
            //2.缓存区读取字符
            byte[] bytes = new byte[readBuffer.remaining()]; //调用flip将buffer截取到最适合值。
            readBuffer.get(bytes);
            result = new String(bytes, "UTF-8");
        }

        try {
            Thread.sleep(1000 * (int)(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

}
