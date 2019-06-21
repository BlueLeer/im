package com.lee.im.server;

import com.lee.im.model.MessageRequestPacket;
import com.lee.im.model.MessageResponsePacket;
import com.lee.im.model.Session;
import com.lee.im.util.SessionUtil;
import io.netty.channel.Channel;
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
        receiveMessage(msg, ctx.channel());
    }

    private void receiveMessage(MessageRequestPacket msg, Channel channel) {
        Session session = SessionUtil.getSession(channel);
        String user = session.getUsername() + "[" + session.getUserId() + "]";
        System.out.println(user + "发来消息: " + msg.getMessage());

        MessageResponsePacket packet = new MessageResponsePacket();

        // 1. 先拿到消息发送方的会话信息
        Session from = SessionUtil.getSession(channel);

        // 2. 根据消息发送方发来的消息构造要发送的消息
        packet.setFromUserId(from.getUserId());
        packet.setFromUsername(from.getUsername());
        packet.setMessage(msg.getMessage());

        // 3.拿到消息接收方的channel
        Channel to = SessionUtil.getChannel(msg.getToUserId());
        if (null == to || !SessionUtil.isLogin(to)) {
            // 构建响应信息
        } else {
            // 构建响应信息
            to.writeAndFlush(packet);
        }
    }
}
