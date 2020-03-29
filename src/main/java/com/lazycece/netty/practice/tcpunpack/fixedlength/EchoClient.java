package com.lazycece.netty.practice.tcpunpack.fixedlength;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author lazycece
 */
public class EchoClient {

    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new EchoClient.ChildChannelHandler());
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new EchoClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient().connect("127.0.0.1", 8080);
    }
}
