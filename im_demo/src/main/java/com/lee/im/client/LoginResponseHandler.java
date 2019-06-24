package com.lee.im.client;

import com.lee.im.model.LoginResponsePacket;
import com.lee.im.model.Session;
import com.lee.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WangLe
 * @date 2019/6/19 14:58
 * @description
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if ("success".equals(msg.getCode())) {
            SessionUtil.bindSession(new Session(msg.getUserId(), msg.getUsername()), ctx.channel());
            System.out.println("[登录成功]");
        } else {
            System.out.println("[登录失败]");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端被关闭!");
    }

}
