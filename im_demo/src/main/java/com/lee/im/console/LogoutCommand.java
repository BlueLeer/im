package com.lee.im.console;

import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/21 15:38
 * @description
 */
public class LogoutCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {

    }

    private void waitForFinish() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
