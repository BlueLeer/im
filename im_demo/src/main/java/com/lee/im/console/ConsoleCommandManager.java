package com.lee.im.console;

import com.lee.im.constant.ConsoleCommandConstant;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author WangLe
 * @date 2019/6/21 15:33
 * @description
 */
public class ConsoleCommandManager {
    private static Map<String, ConsoleCommand> consoleCommandMap;

    private ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put(ConsoleCommandConstant.LOGIN, new LoginCommand());
        consoleCommandMap.put(ConsoleCommandConstant.SEND_TO_USER, new SendToUserCommand());
        consoleCommandMap.put(ConsoleCommandConstant.LOGOUT, new LogoutCommand());
        consoleCommandMap.put(ConsoleCommandConstant.CREATE_GROUP, new CreateGroupCommand());
    }

    public static ConsoleCommand getConsoleCommand(String command) {
        return consoleCommandMap.get(command);
    }

    public void executeCommand(Scanner scanner, Channel channel) {
        System.out.println("请输入指令:");
        String command = scanner.nextLine().trim();
        if (command == null || consoleCommandMap.get(command) == null) {
            System.out.println("指令[" + command + "]无法识别,请重新输入");
        } else {
            consoleCommandMap.get(command).exec(scanner, channel);
        }
    }

    private static class ConsoleCommandManagerHolder {
        private static final ConsoleCommandManager INSTANCE = new ConsoleCommandManager();
    }

    public static ConsoleCommandManager getInstance() {
        return ConsoleCommandManagerHolder.INSTANCE;
    }
}
