package com.learn.nio;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestChannel4 {
    public static void main(String[] args) {
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}