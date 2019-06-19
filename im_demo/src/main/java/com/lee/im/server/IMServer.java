package com.lee.im.server;

import com.lee.im.transcoding.PacketDecoder;
import com.lee.im.transcoding.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author WangLe
 * @date 2019/6/17 16:09
 * @description
 */
public class IMServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 接收新连接的线程,主要负责线程的创建,它相当于传统的IO模型中的accept新的连接的线程组
        NioEventLoopGroup connectGroup = new NioEventLoopGroup();
        // 负责数据的读写线程,处理业务逻辑
        NioEventLoopGroup dataGroup = new NioEventLoopGroup();

        serverBootstrap.group(connectGroup, dataGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new LoginRequestHandler());
                        channel.pipeline().addLast(new MessageRequestHandler());
                        channel.pipeline().addLast(new PacketEncoder());
                    }
                });

        serverBootstrap.bind(8000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("绑定端口8000-----成功!");
            } else {
                System.out.println("绑定端口8000-----失败!");
            }
        });
    }
}
