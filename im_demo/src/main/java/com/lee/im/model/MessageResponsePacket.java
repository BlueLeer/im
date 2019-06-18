package com.lee.im.model;

import com.lee.im.constant.Command;
import lombok.Data;

/**
 * @author WangLe
 * @date 2019/6/18 10:31
 * @description 服务端向客户端发送消息通信数据实体
 */
@Data
public class MessageResponsePacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
