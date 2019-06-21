package com.lee.im.util;

import com.lee.im.constant.Attributes;
import com.lee.im.model.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangLe
 * @date 2019/6/21 9:46
 * @description
 */
public class SessionUtil {
    /**
     * userId->channel的映射
     */
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        if (userIdChannelMap.get(session.getUserId()) == null) {
            userIdChannelMap.put(session.getUserId(), channel);
            channel.attr(Attributes.SESSION).set(session);
        }
    }

    public static void unBindSession(Session session, Channel channel) {
        if (userIdChannelMap.get(session.getUserId()) != null) {
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判定当前的channel当中是否已经有session,如果已经有session了,表明当前用户已经登录了
     *
     * @param channel
     * @return
     */
    public static boolean isLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

}
