package com.lee.im.model;

import com.lee.im.constant.Command;

/**
 * @author WangLe
 * @date 2019/6/24 11:14
 * @description
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
