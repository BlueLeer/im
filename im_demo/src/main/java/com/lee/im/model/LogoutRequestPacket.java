package com.lee.im.model;

import com.lee.im.constant.Command;
import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/21 14:01
 * @description
 */
@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
