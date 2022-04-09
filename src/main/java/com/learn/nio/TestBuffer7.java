package com.learn.nio;

import java.nio.ByteBuffer;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestBuffer7 {
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
        System.out.println(new String(dst, 2, 2));
        System.out.println("position: " + buf.position());
    }
}