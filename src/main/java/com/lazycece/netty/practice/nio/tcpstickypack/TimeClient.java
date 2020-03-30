package com.lazycece.netty.practice.nio.tcpstickypack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lazycece
 */
public class TimeClient {

    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new TimeClient.ChildChannelHandler());
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new TimeClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TimeClient().connect("127.0.0.1", 8080);
    }
}
