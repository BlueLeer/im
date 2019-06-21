package com.lee.im.model;

import com.lee.im.constant.Command;
import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/21 14:15
 * @description
 */
@Data
public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
