package com.lee.im.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WangLe
 * @date 2019/6/20 14:57
 * @description 统计当前连接服务器的连接数
 */
public class ConnectionCounterHandler extends ChannelInboundHandlerAdapter {
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int andIncrement = counter.incrementAndGet();
        System.out.println("[连接]当前连接服务端的客户端连接数为: " + andIncrement);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        int andIncrement = counter.decrementAndGet();
        System.out.println("[断开]当前连接服务端的客户端连接数为: " + andIncrement);
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
