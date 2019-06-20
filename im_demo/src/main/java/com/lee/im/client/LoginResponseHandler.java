package com.lee.im.client;

import com.lee.im.model.LoginRequestPacket;
import com.lee.im.model.LoginResponsePacket;
import com.lee.im.transcoding.PacketTransCode;
import com.lee.im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author WangLe
 * @date 2019/6/19 14:58
 * @description
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.getCode().equals("success")) {
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
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(getUserId());
        packet.setUsername(username);
        packet.setPassword("123");

        // 编码
        PacketTransCode serializer = PacketTransCode.getInstance();
        ByteBuf encode = serializer.encode(ctx.alloc().ioBuffer(), packet);

        // 发送登陆数据
        ctx.writeAndFlush(encode);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端被关闭!");
    }

    /**
     * 随机生成四位数的用户ID
     *
     * @return
     */
    private String getUserId() {
        int asInt = IntStream.range(1000, 9999).findAny().getAsInt();
        return String.valueOf(asInt);
    }
}
