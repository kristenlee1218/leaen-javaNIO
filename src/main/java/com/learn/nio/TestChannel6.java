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
public class TestChannel6 {
    public static void main(String[] args) throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\nio.zip"), StandardOpenOption.READ);
        // 注意：StandardOpenOption.CREATE
        // 如果文件已经存在，直接覆盖，StandardOpenOption.CREATE_NEW,如果文件已经存在，就抛出异常。
        FileChannel outChannel = FileChannel.open(Paths.get("E:\\nio.zip"), StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        // inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
    }
}