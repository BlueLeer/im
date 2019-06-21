package com.lee.im.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author WangLe
 * @date 2019/6/21 15:32
 * @description
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
