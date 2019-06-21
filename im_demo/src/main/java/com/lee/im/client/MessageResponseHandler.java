package com.lee.im.client;

import com.lee.im.model.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WangLe
 * @date 2019/6/19 15:00
 * @description
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        String response = msg.getFromUsername() + "[" + msg.getFromUserId() + "]发来消息:" + msg.getMessage();
        System.out.println(response);
    }
}
