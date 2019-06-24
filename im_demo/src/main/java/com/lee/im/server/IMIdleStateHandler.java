package com.lee.im.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/24 10:51
 * @description
 * 加入此空闲检测的处理器,服务端会在15秒内如果没有读到数据,就会关闭当前的channel
 * 但是15秒内没有读取到客户端发来的数据,服务端并不能关闭连接,此时为了防止服务端误判,必须在客户端加入心跳检测
 */
public class IMIdleStateHandler extends IdleStateHandler {
    public static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        // 四个参数:
        // 读空闲时间,表示这段时间内如果没有数据读到,就表示连接假死了
        // 写空闲时间,表示这段时间内,如果没有写数据,就表示连接假死
        // 读写空闲时间,表示这段时间内,如果没有读写数据,就表示连接假死,读写空闲时间为0表示我们不关心这两类事件
        // 时间单位
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    /**
     * 在上面定义的[15秒钟内没有发生读数据,就会关闭本条连接的时候进行回调]
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println("[" + READER_IDLE_TIME + "]内未读到数据,关闭连接");
        ctx.channel().close();
    }
}
