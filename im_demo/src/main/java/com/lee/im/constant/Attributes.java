package com.lee.im.constant;

import io.netty.util.AttributeKey;

/**
 * @author WangLe
 * @date 2019/6/18 10:56
 * @description
 */
public final class Attributes {
    public static final AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    public static final AttributeKey<Boolean> USERNAME = AttributeKey.newInstance("username");

}
