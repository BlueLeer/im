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

    /**
     * 客户端登出请求指令
     */
    public static final Byte LOGOUT_REQUEST = 5;

    /**
     * 客户端登出响应指令
     */
    public static final Byte LOGOUT_RESPONSE = 6;

    /**
     * 客户端创建群聊请求指令
     */
    public static final Byte CREATE_GROUP_REQUEST = 7;

    /**
     * 服务端创建群聊响应指令
     */
    public static final Byte CREATE_GROUP_RESPONSE = 8;

    /**
     * 客户端心跳检测请求指令
     */
    public static final Byte HEART_BEAT_REQUEST = 9;

    /**
     * 服务端心跳检测响应指令
     */
    public static final Byte HEART_BEAT_RESPONSE = 10;


}
