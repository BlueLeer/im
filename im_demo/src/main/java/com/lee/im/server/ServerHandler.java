package com.lee.im.server;

import com.lee.im.model.LoginRequestPacket;
import com.lee.im.model.LoginResponsePacket;
import com.lee.im.model.Packet;
import com.lee.im.transcoding.PacketTransCode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author WangLe
 * @date 2019/6/17 16:10
 * @description
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketTransCode.getInstance().decode(byteBuf);

        // 判断是否登陆请求的数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            // 登陆校验
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            if (validate(loginRequestPacket)) {
                responsePacket.setCode("success");
                responsePacket.setMsg("登陆成功");
            } else {
                responsePacket.setCode("fail");
                responsePacket.setMsg("登陆失败");
            }

            ByteBuf responseByteBuf = PacketTransCode.getInstance().encode(ctx.alloc().ioBuffer(), responsePacket);
            // 向客户端写入响应消息
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean validate(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
