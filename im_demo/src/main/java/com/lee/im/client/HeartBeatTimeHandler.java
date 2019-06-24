package com.lee.im.client;

import com.lee.im.model.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/24 11:02
 * @description
 */
public class HeartBeatTimeHandler extends ChannelInboundHandlerAdapter {
    public static final int HEART_BEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        // 表示还要继续往下传播
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        // ctx.executor()返回的是当前channel绑定的NIO线程,schedule()类似于jdk的延时任务机制
        // 每隔5秒钟客户端会向服务端发送一个心跳检测包,这个时间段通常来说应该比服务端的假死连接检测时间的一半要短,所以直接定义为三分之一
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEART_BEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
