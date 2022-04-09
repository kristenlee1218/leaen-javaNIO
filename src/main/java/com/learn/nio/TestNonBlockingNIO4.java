package com.learn.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Scanner;

/**
 * @author : Kristen
 * @date : 2022/4/9
 * @description :
 */
public class TestNonBlockingNIO4 {
    @Test
    public void send() {
        DatagramChannel dc = null;
        try {
            dc = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dc.configureBlocking(false);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNext()) {
                String str = scan.next();
                buf.put((new Date().toString() + "\n" + str).getBytes());
                buf.flip();
                try {
                    dc.send(buf, new InetSocketAddress("127.0.0.1", 9898));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buf.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dc != null) {
                try {
                    dc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void receive() {
        DatagramChannel dc = null;
        try {
            dc = DatagramChannel.open();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            dc.configureBlocking(false);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            dc.bind(new InetSocketAddress(9898));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Selector selector = null;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dc.register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
        try {
            while (selector.select() > 0) {
                for (SelectionKey sk : selector.selectedKeys()) {
                    if (sk.isReadable()) {
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        dc.receive(buf);
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, buf.limit()));
                        buf.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}