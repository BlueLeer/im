package com.lee.im.constant;

/**
 * @author WangLe
 * @date 2019/6/17 14:21
 * @description 指令类相关的枚举类
 */
public final class Command {
    /**
     * 客户端登录请求指令
     */
    public static final Byte LOGIN_REQUEST = 1;


    /**
     * 客户端登陆成功以后服务端响应的指令
     */
    public static final Byte LOGIN_RESPONSE = 2;

    /**
     * 客户端发送至服务器的消息指令
     */
    public static final Byte MESSAGE_REQUEST = 3;

    /**
     * 服务器发送至客户端的消息指令
     */
    public static final Byte MESSAGE_RESPONSE = 4;


}
