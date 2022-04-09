package com.learn.nio;

import java.nio.ByteBuffer;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestBuffer8 {
    public static void main(String[] args) {
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println("position: " + buf.position());
        buf.mark();
        buf.get(dst, 2, 2);
        // reset()：找出 mark 的位置
        buf.reset();
        System.out.println(buf.position());
        // 判断缓冲区是否还有剩余的数据
        if (buf.hasRemaining()) {
            // 获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }
    }
}