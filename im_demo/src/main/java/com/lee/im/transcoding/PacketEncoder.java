package com.lee.im.transcoding;

import com.lee.im.model.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author WangLe
 * @date 2019/6/19 14:40
 * @description 将对象转化成二进制数据
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketTransCode.getInstance().encode(out, msg);
    }
}
