package com.lee.im.server;

import com.lee.im.model.LoginRequestPacket;
import com.lee.im.model.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WangLe
 * @date 2019/6/19 14:27
 * @description 登录请求逻辑处理器
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        // 登录逻辑
        LoginResponsePacket responsePacket = login(msg);
        // 向客户端写入响应消息,直接写入响应的对象,后面有PacketEncoder进行编码
        ctx.channel().writeAndFlush(responsePacket);
    }

    private LoginResponsePacket login(LoginRequestPacket msg) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        if (validate(msg)) {
            responsePacket.setCode("success");
            responsePacket.setMsg("登陆成功");

        } else {
            responsePacket.setCode("fail");
            responsePacket.setMsg("登陆失败");
        }
        return responsePacket;
    }

    private boolean validate(LoginRequestPacket loginRequestPacket) {
        System.out.println("用户名:" + loginRequestPacket.getUsername());
        System.out.println("密码:" + loginRequestPacket.getPassword());
        return true;
    }

}
