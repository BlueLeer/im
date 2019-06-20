package com.lee.im.transcoding;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author WangLe
 * @date 2019/6/20 9:58
 * @description
 */
public class MagicFilter extends LengthFieldBasedFrameDecoder {
    /**
     * 长度域的偏移量
     */
    private static final int LENGTH_FIELD_OFFSET = 7;

    /**
     * 长度域的字节长度,这里定义的是int,所以长度为4
     */
    private static final int LENGTH_FIELD_LENGTH = 4;

    public MagicFilter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议的的客户端
        if (in.getInt(in.readerIndex()) != PacketTransCode.MAGIC_NUMBER) {
            // 关闭本条连接,释放资源
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
