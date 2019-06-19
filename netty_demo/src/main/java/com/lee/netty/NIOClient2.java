package com.lee.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
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
 * ChannelInboundHandler 处理数据的逻辑,比如，我们在一端读到一段数据，首先要解析这段数据，然后对这些数据做一系列逻辑处理，最终把响应写到对端，
 * 在开始组装响应之前的所有的逻辑，都可以放置在 ChannelInboundHandler 里处理，它的一个最重要的方法就是 channelRead()
 * 可以将 ChannelInboundHandler 的逻辑处理过程与 TCP 的七层协议的解析联系起来，收到的数据一层层从物理层上升到我们的应用层
 */
public class NIOClient2 {

    /**
     * 最大重连次数--5次
     */
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        bootstrap
                // 指定线程模型
                .group(clientGroup)
                .attr(AttributeKey.newInstance("date"), new Date())
                // 指定IO模型
                .channel(NioSocketChannel.class)
                // 设置连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .option(ChannelOption.TCP_NODELAY, true)
                // 是否开启TCP底层心跳机制
                .option(ChannelOption.SO_KEEPALIVE, true)
                // IO处理逻辑
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new ChannelInboundHandlerA());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);

    }

    static class ChannelInboundHandlerA extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < 100; i++) {
                ctx.channel().writeAndFlush(getByteBuf(ctx));
            }
        }

        private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
            byte[] bytes = "Hello，我是王乐，我的英文名是Lee！".trim().getBytes(Charset.forName("utf-8"));
            System.out.println(new String(bytes, Charset.forName("utf-8")));
            ByteBuf buffer = ctx.alloc().buffer();
            System.out.println(buffer.readableBytes());
            buffer.writeInt(bytes.length);
            System.out.println(buffer.readableBytes());
            buffer.writeBytes(bytes);
            System.out.println(buffer.readableBytes());
            System.out.println("length" + bytes.length);

            return buffer;
        }
    }


    /**
     * @param bootstrap
     * @param host      主机
     * @param port      端口号
     * @param retry     剩余重连次数
     *                  <p>
     *                  通常情况下,连接建立失败不会立即重新连接,而是会通过一个"指数退避"的方式,比如每隔1、2、4、16秒，以2的幂次来建立连接
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
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);

            }
        });
    }
}
