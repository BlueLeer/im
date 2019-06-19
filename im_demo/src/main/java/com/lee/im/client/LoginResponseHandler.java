package com.lee.im.client;

import com.lee.im.model.LoginResponsePacket;
import com.lee.im.util.LoginUtil;
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

        System.out.println("服务端返回的登录状态码:" + msg.getCode());
        System.out.println("服务端返回的登录消息内容:" + msg.getMsg());

        if (msg.getCode().equals("success")) {
            LoginUtil.markAsLogin(ctx.channel());
        }
    }
}
