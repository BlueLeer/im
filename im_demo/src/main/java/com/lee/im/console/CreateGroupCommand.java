package com.lee.im.console;

import com.lee.im.model.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/21 15:38
 * @description
 */
public class CreateGroupCommand implements ConsoleCommand {
    public static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket groupRequestPacket = new CreateGroupRequestPacket();
        System.out.print("[拉人群聊]输入userId列表,userId之间英文逗号隔开:");
        String userIds = scanner.nextLine();

        groupRequestPacket.setUserIdList(new ArrayList<>(Arrays.asList(userIds.trim().split(USER_ID_SPLITER))));
        channel.writeAndFlush(groupRequestPacket);
        waitForFinish();
    }

    private void waitForFinish() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
