package com.lee.im.server;

import com.lee.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author WangLe
 * @date 2019/6/20 15:54
 * @description
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.isLogin(ctx.channel())) {
            System.out.println("用户未登陆,无法发送消息");
            ctx.channel().close();
        } else {
            // 已经登录了,不需要处理登录校验的逻辑Handler了,所以此处将其移除
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.isLogin(ctx.channel())) {
            System.out.println("当前连接登录完毕,无需再次验证,移除AuthHandler...");
        } else {
            System.out.println("无登录验证,强制关闭连接...");
        }
    }
}
