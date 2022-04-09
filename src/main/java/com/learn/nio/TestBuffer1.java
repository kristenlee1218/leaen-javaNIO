package com.learn.nio;

import java.nio.ByteBuffer;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestBuffer1 {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("position: " + buf.position());
        System.out.println("limit: " + buf.limit());
        System.out.println("capacity: " + buf.capacity());
    }
}