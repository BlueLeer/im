package com.lee.im.model;

import com.lee.im.constant.Command;
import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/17 14:25
 * @description 登录请求数据实体
 */
@Data
public class LoginRequestPacket extends Packet {
    /**
     * id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
