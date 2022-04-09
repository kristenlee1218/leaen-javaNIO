package com.learn.nio;

import java.nio.ByteBuffer;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description : 清空缓冲区、但是缓冲区的数据依然存在、
 * 但是处于 “被遗忘” 的状态
 */
public class TestBuffer6 {
    public static void main(String[] args) {
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        buf.rewind();
        buf.clear();
        System.out.println("position: " + buf.position());
        System.out.println("limit: " + buf.limit());
        System.out.println("capacity: " + buf.capacity());
        System.out.println("(char)buf.get(): " + (char) buf.get());
    }
}