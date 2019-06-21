package com.lee.im.model;

import com.lee.im.constant.Command;
import lombok.Data;

import java.util.List;

/**
 * @author WangLe
 * @date 2019/6/21 15:52
 * @description
 */
@Data
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
