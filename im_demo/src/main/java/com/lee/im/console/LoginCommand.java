package com.lee.im.console;

import com.lee.im.model.LoginRequestPacket;
import com.lee.im.util.LoginUtil;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/21 16:01
 * @description
 */
public class LoginCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("用户登录,请输入用户名,请在这里面进行选择[lee, zhangsan, lisi, wangmazi]:");
        String username = scanner.nextLine();

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(LoginUtil.getUserId());
        packet.setUsername(username);
        packet.setPassword("123");

        channel.writeAndFlush(packet);
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
