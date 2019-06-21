package com.lee.im.server;

import com.lee.im.constant.Command;
import com.lee.im.model.LogoutRequestPacket;
import com.lee.im.model.Session;
import com.lee.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WangLe
 * @date 2019/6/21 14:21
 * @description
 */
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        if (Command.LOGOUT_REQUEST.equals(msg.getCommand())) {
            // 根据当前的channel找到当前的session
            Session session = SessionUtil.getSession(ctx.channel());
            SessionUtil.unBindSession(session, ctx.channel());
            String print = session.getUsername() + "[" + session.getUserId() + "]退出登录";
            System.out.println(print);
        }
    }
}
