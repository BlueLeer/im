package com.lee.im.client;

import com.lee.im.model.LoginRequestPacket;
import com.lee.im.model.LoginResponsePacket;
import com.lee.im.model.Packet;
import com.lee.im.transcoding.PacketTransCode;
import com.lee.im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @author WangLe
 * @date 2019/6/17 16:04
 * @description
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ":客户端开始登陆");

        // 创建登陆对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("lee");
        packet.setPassword("123");

        // 编码
        PacketTransCode serializer = PacketTransCode.getInstance();
        ByteBuf encode = serializer.encode(ctx.alloc().ioBuffer(), packet);

        // 发送登陆数据
        ctx.channel().writeAndFlush(encode);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketTransCode.getInstance().decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;

            System.out.println("服务端返回的登录状态码:" + responsePacket.getCode());
            System.out.println("服务端返回的登录消息内容:" + responsePacket.getMsg());

            if ("success".equals(responsePacket.getCode())) {
                LoginUtil.markAsLogin(ctx.channel());
            }
        }
    }
}
