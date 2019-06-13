package com.lee.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author WangLe
 * @date 2019/6/13 11:20
 * @description 能够实现双向通信的Netty的客户端
 */
public class NIOClient {

    // 最大重连次数--5次
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        bootstrap
                // 指定线程模型
                .group(clientGroup)
                .attr(AttributeKey.newInstance("date"),new Date())
                // 指定IO模型
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000) // 设置连接超时时间
                .option(ChannelOption.SO_KEEPALIVE,true) // 是否开启TCP底层心跳机制
                // IO处理逻辑
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("客户端写出数据:");
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes("Hello Server!".getBytes(Charset.forName("utf-8")));

                                ctx.channel().writeAndFlush(buffer);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println("客户端收到服务端的响应为:" + byteBuf.toString(Charset.forName("utf-8")));
                            }
                        });

                        System.out.println("date: "+channel.attr(AttributeKey.valueOf("date")).get().toString());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);

    }

    /**
     * @param bootstrap
     * @param host      主机
     * @param port      端口号
     * @param retry     剩余重连次数
     *
     * 通常情况下,连接建立失败不会立即重新连接,而是会通过一个"指数退避"的方式,比如每隔1、2、4、16秒，以2的幂次来建立连接
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.out.println("重连次数已用完,放弃连接!");
            } else {
                int currentRetry = (MAX_RETRY - retry) + 1;
                // 2进制有符号左移一位相当于取一次平方
                int delay = 1 << currentRetry;
                System.out.println("连接失败,开始重新连接!");

                // bootstrap.config().group() 返回配置的线程模型
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry-1),delay, TimeUnit.SECONDS);

            }
        });
    }
}
