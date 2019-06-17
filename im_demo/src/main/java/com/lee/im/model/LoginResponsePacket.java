package com.lee.im.model;

import com.lee.im.constant.Command;
import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/17 16:36
 * @description
 */
@Data
public class LoginResponsePacket extends Packet {
    private String code;
    private String msg;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
