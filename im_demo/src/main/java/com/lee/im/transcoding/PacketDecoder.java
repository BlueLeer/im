package com.lee.im.transcoding;

import com.lee.im.model.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author WangLe
 * @date 2019/6/19 14:51
 * @description 将对二进制数据转换成对象,并添加到out中返回,供下一个Handler使用
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketTransCode.getInstance().decode(in));
    }
}
