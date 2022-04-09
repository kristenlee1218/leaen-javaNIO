package com.learn.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestChannel5 {
    public static void main(String[] args) {
        Charset cs = Charset.forName("GBK");
        // 获取编码器
        CharsetEncoder ce = cs.newEncoder();
        // 获取解码器
        CharsetDecoder cd = cs.newDecoder();
        CharBuffer cb = CharBuffer.allocate(1024);
        cb.put("测试");
        cb.flip();
        // 编码
        ByteBuffer buf = null;
        try {
            buf = ce.encode(cb);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 4; i++) {
            System.out.println(buf.get());
        }
        // 解码
        buf.flip();
        CharBuffer cb2 = null;
        try {
            cb2 = cd.decode(buf);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        System.out.println(cb2.toString());
        System.out.println("---------------------------");
        Charset cs2 = StandardCharsets.UTF_8;
        buf.flip();
        CharBuffer cb3 = cs2.decode(buf);
        System.out.println(cb3.toString());
    }
}