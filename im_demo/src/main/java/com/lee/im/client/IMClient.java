package com.lee.im.client;

import com.lee.im.model.MessageRequestPacket;
import com.lee.im.transcoding.PacketTransCode;
import com.lee.im.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/17 16:05
 * @description
 */
public class IMClient {
    // 最大重连次数--5次
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });

        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");

                // 连接成功之后,启动控制台线程
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.out.println("重连次数已用完,放弃连接!");
            } else {
                int currentRetry = (MAX_RETRY - retry) + 1;
                // 2进制有符号左移一位相当于取一次平方
                int delay = 1 << currentRetry;
                System.out.println("连接失败,开始重新连接!");

                // bootstrap.config().group() 返回配置的线程模型
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);

            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.isLogin(channel)) {
                    System.out.println("请输入消息发送至服务端: ");
                    Scanner scanner = new Scanner(System.in);
                    String s = scanner.nextLine();

                    // 组装发送消息的对象
                    MessageRequestPacket requestPacket = new MessageRequestPacket();
                    requestPacket.setMessage(s);
                    ByteBuf encode = PacketTransCode.getInstance().encode(channel.alloc().ioBuffer(), requestPacket);
                    channel.writeAndFlush(encode);
                }
            }
        }).start();
    }
}
