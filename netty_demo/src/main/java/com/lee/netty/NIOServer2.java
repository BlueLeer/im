package com.lee.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * @author WangLe
 * @date 2019/6/13 10:55
 * @description 能够实现双向通信的Netty的服务端
 * <p>
 * 总结:要启动一个Netty的服务端,必须要指定三类属性:线程模型、IO模型、连接数据读写处理逻辑，之后再调用bind()绑定端口即可
 */
public class NIOServer2 {

    public static void main(String[] args) {
        startSimpleServer();
//        startServer();
    }

    /**
     * 启动一个简单的Netty服务端
     */
    private static void startSimpleServer() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 接收新连接的线程,主要负责线程的创建,它相当于传统的IO模型中的accept新的连接的线程组
        NioEventLoopGroup connectGroup = new NioEventLoopGroup();
        // 负责数据的读写线程,处理业务逻辑
        NioEventLoopGroup dataGroup = new NioEventLoopGroup();

        serverBootstrap
                // 给引导类配置两大线程组,这个引导类的线程模型也就确定下来了,这里都是NIO模型
                .group(connectGroup, dataGroup)
                // 指定服务器端的IO模型
                .channel(NioServerSocketChannel.class)
                // 定义后续每条连接的数据读写,业务处理逻辑
                // 这里指定服务端能够处理的连接,也就是客户端类型为NioSocketChannel类型的客户端连接
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        // 加入拆包器
                        channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
                        // 在这里进行数据的读写
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println("服务端读取到的数据:" + byteBuf.toString(Charset.forName("utf-8")));
                            }
                        });

                    }


                });
        // 绑定服务端的端口号,接收8000端口的客户端连接
        // serverBootstrap.bind(8000);

        // 绑定服务端的端口号,接收8000端口的客户端连接
        // bind方法是一个异步的方法,调用之后是立即返回的,添加一个监听器,可以监测短裤是否绑定成功
        serverBootstrap.bind(8000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("绑定端口8000-----成功!");
            } else {
                System.out.println("绑定端口8000-----失败!");
            }
        });
    }

    /**
     * 演示NettyServer常用的方法
     */
    private static void startServer() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 接收新连接的线程,主要负责线程的创建,它相当于传统的IO模型中的accept新的连接的线程组
        NioEventLoopGroup connectGroup = new NioEventLoopGroup();
        // 负责数据的读写线程,处理业务逻辑
        NioEventLoopGroup dataGroup = new NioEventLoopGroup();

        int index = 0;
        serverBootstrap
                // 给服务端的channel,也就是后面指定的NioServerSocketChannel指定一些自定义的属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                // 给服务端channel设置一些属性
                .option(ChannelOption.SO_BACKLOG, 1024) // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                // 给每一条连接指定自定义的属性
                .childAttr(AttributeKey.newInstance("childName"), index++)
                // 给每一条连接设置一些TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.TCP_NODELAY, true) //表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                // 给引导类配置两大线程组,这个引导类的线程模型也就确定下来了,这里都是NIO模型
                .group(connectGroup, dataGroup)
                // 指定服务器端的IO模型
                .channel(NioServerSocketChannel.class)
                // 用于指定在服务端启动过程中的一些逻辑,通常情况下很少使用
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel channel) throws Exception {
                        // 获取前面指定的attr的值
                        Attribute<Object> serverName = channel.attr(AttributeKey.valueOf("serverName"));
                        System.out.println("当前服务端的Name为: " + serverName.get());
                    }

                })
                // 定义后续每条连接的数据读写,业务处理逻辑
                // 这里指定服务端能够处理的连接,也就是客户端类型为NioSocketChannel类型的客户端连接
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        // 获取前面指定的childAttr指定的属性
                        Attribute<Object> childName = channel.attr(AttributeKey.valueOf("childName"));
                        System.out.println("当前客户端的Name为: " + childName.get());
                        // 在这里进行数据的读写

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
