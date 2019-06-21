package com.lee.im.console;

import com.lee.im.model.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/21 15:37
 * @description
 */
public class SendToUserCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("发送消息到指定的用户,格式为[userId:消息内容]");
        String s = scanner.nextLine().trim();
        if (s.split(":").length != 2) {
            System.out.println("输入格式有误");
        } else {
            String[] userIdAndContent = s.split(":");
            String userId = userIdAndContent[0];
            String content = userIdAndContent[1];

            MessageRequestPacket packet = new MessageRequestPacket();
            packet.setToUserId(userId);
            packet.setMessage(content);

            channel.writeAndFlush(packet);
            waitForFinish();
        }
    }

    private void waitForFinish() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
