package com.learn.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestNonBlockingNIO3 {
    @Test
    public void client() {
        // 获取通道
        SocketChannel sChannel = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 切换非阻塞模式
        try {
            sChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 发送数据给服务器
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNext()) {
                String str = scan.next();
                buf.put((new Date().toString() + "\n" + str).getBytes());
                buf.flip();
                try {
                    sChannel.write(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buf.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭通道
            if (sChannel != null) {
                try {
                    sChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void server() {
        // 获取通道
        ServerSocketChannel ssChannel = null;
        try {
            ssChannel = ServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 切换非阻塞模式
        try {
            ssChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 绑定链接
        try {
            ssChannel.bind(new InetSocketAddress(9898));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取选择器
        Selector selector = null;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将通道注册到选择器上、并制定监听接收事件
        try {
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 轮巡视的获取选择器上已经准备就绪的事件
        try {
            while (selector.select() > 0) {
                // 获取当前选择器中所注册的选择键（已就绪的监听事件）
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    // 获取准备就绪的是事件
                    SelectionKey sk = it.next();
                    // 判断具体是什么事件准备就绪
                    if (sk.isAcceptable()) {
                        // 若准备就绪、获取客户端连接
                        SocketChannel sChannel = ssChannel.accept();
                        // 切换非阻塞模式
                        sChannel.configureBlocking(false);
                        // 将该通道注册到选择器上
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {
                        // 获取当前选择器上“读就绪”状态的通道
                        SocketChannel sChannel = (SocketChannel) sk.channel();
                        // 读数据
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = sChannel.read(buf)) > 0) {
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, len));
                            buf.clear();
                        }
                    }
                    // 取消选择键SelectionKey
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}