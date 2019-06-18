package com.lee.im.model;

import com.lee.im.constant.Command;
import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/18 10:29
 * @description 客户端发送消息数据实体
 */
@Data
public class MessageRequestPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
