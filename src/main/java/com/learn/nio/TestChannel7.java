package com.learn.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestChannel7 {
    public static void main(String[] args) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile("/Users/kristen/IdeaProjects/1.txt", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 获取通道
        FileChannel channel1 = raf.getChannel();
        // 分配指定代销的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        // 分散读数据
        ByteBuffer[] bufs = {buf1, buf2};
        try {
            channel1.read(bufs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
        // 聚集写入
        RandomAccessFile raf2 = null;
        try {
            raf2 = new RandomAccessFile("/Users/kristen/IdeaProjects/2.txt", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel channel2 = raf2.getChannel();
        try {
            channel2.write(bufs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}