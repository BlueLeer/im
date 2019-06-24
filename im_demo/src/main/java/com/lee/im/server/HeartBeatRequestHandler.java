package com.lee.im.server;

import com.lee.im.model.HeartBeatRequestPacket;
import com.lee.im.model.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WangLe
 * @date 2019/6/24 11:23
 * @description
 */
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        System.out.println("收到心跳包");
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
