package com.learn.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestPipe {
    @Test
    public void test() {
        // 获取管道
        Pipe pipe = null;
        try {
            pipe = Pipe.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将缓冲区的数据写入管道
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Pipe.SinkChannel sinkChannel = pipe.sink();
        buf.put("通过単向管道发送数据".getBytes());
        buf.flip();
        try {
            sinkChannel.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        int len = 0;
        try {
            len = sourceChannel.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(buf.array(), 0, len));
        try {
            sourceChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sinkChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}