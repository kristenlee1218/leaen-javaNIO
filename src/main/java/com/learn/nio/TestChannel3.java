package com.learn.nio;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestChannel3 {
    public static void main(String[] args) {
        // 通道之间的数据传输（直接缓冲区）
        FileChannel inChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("/Users/kristen/IdeaProjects/1.png"), StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileChannel outChannel = null;
        try {
            outChannel = FileChannel.open(Paths.get("/Users/kristen/IdeaProjects/2.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outChannel.transferFrom(inChannel, 0, inChannel.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                try {
                    inChannel.close();
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
        }
    }
}