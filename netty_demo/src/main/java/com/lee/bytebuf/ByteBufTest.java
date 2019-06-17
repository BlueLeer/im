package com.lee.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.Charset;

/**
 * @author WangLe
 * @date 2019/6/13 16:28
 * @description
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(8, 100);
        buffer.writeInt(10);
        buffer.writeInt(20);
        buffer.writeInt(100);

//        System.out.println("maxCapacity: " + buffer.maxCapacity());
//        System.out.println("capacity: " + buffer.capacity());
//        System.out.println("readerIndex: " + buffer.readerIndex());
//        System.out.println("writerIndex: " + buffer.writerIndex());
//        int i = buffer.readInt();
//        System.out.println(i);
//        System.out.println("readerIndex: " + buffer.readerIndex());
//        testSlice(buffer);

        testByte();
    }

    /**
     * 测试ByteBuf的slice方法
     */
    private static void testSlice(ByteBuf byteBuf) {
        ByteBuf slice = byteBuf.slice();
        System.out.println("byteBuf[maxCapacity]: " + byteBuf.maxCapacity());
        System.out.println("slice[maxCapacity]: " + slice.maxCapacity());

        // slice方法得到的ByteBuf对象,它们的底层内存以及引用计数都是与原始的ByteBuf共享,维护着自己的读写指针
        int i = slice.readInt();
        System.out.println(i);

        System.out.println("byteBuf[readerIndex]: " + byteBuf.readerIndex());
        System.out.println("slice[readerIndex]: " + slice.readerIndex());
        System.out.println("byteBuf[readableBytes读数据之前:" + byteBuf.readableBytes());
        slice.readInt();
        System.out.println("byteBuf[readableBytes]读数据之后:" + byteBuf.readableBytes());

        slice.resetReaderIndex(); // 重置读指针
        System.out.println("重置指针以后当前可读的字节数:" + slice.readableBytes()); // 应该为12字节

    }

    private static void testByte() {
        String s = String.valueOf(0xcafebabe);
        System.out.println(s);
    }
}
