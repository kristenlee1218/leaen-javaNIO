package com.learn.nio;

import java.nio.ByteBuffer;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestBuffer5 {
    public static void main(String[] args) {
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        buf.rewind();
        System.out.println("position: " + buf.position());
        System.out.println("limit: " + buf.limit());
        System.out.println("capacity: " + buf.capacity());
    }
}