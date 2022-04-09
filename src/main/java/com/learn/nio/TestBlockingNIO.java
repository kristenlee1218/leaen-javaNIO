package com.learn.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestBlockingNIO {

    /**
     * 1、使用NIO完成网络通信的三个核心
     * （1）、通道（Channel）：负责连接
     * java.nio.channels.Channel接口
     * |--SelectableChannel
     * |--SocketChannel
     * |--ServerSocketChannel
     * |--DatagramChannel
     * <p>
     * |--Pipe.SinkChannel
     * |--Pipe.SourceChannel
     * <p>
     * （2）、缓冲区（Buffer）：负责数据的存取
     * （3）、选择器（Selector）：是SelectableChannel的多路复用器、用于监控SelectableChannel的IO状况
     */
    @Test
    public void client() {
        // 获取通道
        SocketChannel sChannel = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("192.168.0.107", 9898));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileChannel inChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 读取本地文件并发送到服务器
        try {
            while (inChannel.read(buf) != -1) {
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭通道
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        FileChannel outChannel = null;
        try {
            outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 绑定链接
        try {
            ssChannel.bind(new InetSocketAddress(9898));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取客户端连接的通道
        SocketChannel sChannel = null;
        try {
            sChannel = ssChannel.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 接收客户端的数据、保存到本地
        try {
            while (sChannel.read(buf) != -1) {
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sChannel != null) {
                // 关闭通道
                try {
                    sChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                ssChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}