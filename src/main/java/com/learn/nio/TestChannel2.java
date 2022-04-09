package com.learn.nio;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestChannel2 {
    public static void main(String[] args) {
        FileChannel inChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("/Users/kristen/IdeaProjects/1.png"), StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileChannel outChannel = null;
        try {
            outChannel = FileChannel.open(Paths.get("/Users/kristen/IdeaProjects/2.png"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 内存映射文件
        MappedByteBuffer inMappedBuf = null;
        try {
            inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MappedByteBuffer outMappedBuf = null;
        try {
            outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 直接对缓冲区进行数据的读写操作
            byte[] dst = new byte[inMappedBuf.limit()];
            inMappedBuf.get(dst);
            outMappedBuf.put(dst);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}