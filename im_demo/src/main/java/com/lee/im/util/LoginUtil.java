package com.lee.im.util;

import com.lee.im.constant.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author WangLe
 * @date 2019/6/18 10:58
 * @description
 */
public class LoginUtil {
    /**
     * 标记当前的channel(客户端)的登录状态--已登录
     *
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断是否已经登录
     *
     * @param channel
     * @return
     */
    public static boolean isLogin(Channel channel) {
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        if (attr == null) {
            return false;
        } else {
            return attr.get() != null;
        }
    }
}
