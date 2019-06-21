package com.lee.im.client;

import com.lee.im.model.LoginRequestPacket;
import com.lee.im.model.LoginResponsePacket;
import com.lee.im.transcoding.PacketTransCode;
import com.lee.im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author WangLe
 * @date 2019/6/19 14:58
 * @description
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if ("success".equals(msg.getCode())) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println("[登录成功]");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("<请输入用户名登录>");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        // 创建登陆对象


        waitForLogin();
    }

    private void waitForLogin() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端被关闭!");
    }

}
