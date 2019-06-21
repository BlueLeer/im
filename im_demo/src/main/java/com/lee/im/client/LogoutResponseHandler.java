package com.lee.im.client;

import com.lee.im.constant.Attributes;
import com.lee.im.constant.Command;
import com.lee.im.model.LogoutResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WangLe
 * @date 2019/6/21 14:16
 * @description
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if (msg.getCommand().equals(Command.LOGOUT_RESPONSE)) {
            ctx.channel().attr(Attributes.LOGIN).set(false);
        }
    }
}
