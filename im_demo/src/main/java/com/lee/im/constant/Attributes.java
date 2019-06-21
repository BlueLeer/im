package com.lee.im.constant;

import com.lee.im.model.Session;
import io.netty.util.AttributeKey;

/**
 * @author WangLe
 * @date 2019/6/18 10:56
 * @description
 */
public final class Attributes {
    public static final AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    public static final AttributeKey<Boolean> USERNAME = AttributeKey.newInstance("username");
    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");


}
