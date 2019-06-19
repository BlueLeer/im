package com.lee.im.server;

import com.lee.im.model.MessageRequestPacket;
import com.lee.im.model.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WangLe
 * @date 2019/6/19 14:31
 * @description 消息请求逻辑处理器
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        // 直接写入响应的对象,后面有PacketEncoder进行编码
        ctx.channel().writeAndFlush(receiveMessage(msg));
    }

    private MessageResponsePacket receiveMessage(MessageRequestPacket msg) {
        System.out.println("客户端发来消息: " + msg.getMessage());
        MessageResponsePacket packet = new MessageResponsePacket();
        packet.setMessage("收到客户端发来的消息,消息长度为: " + msg.getMessage().trim().length());
        return packet;
    }
}
