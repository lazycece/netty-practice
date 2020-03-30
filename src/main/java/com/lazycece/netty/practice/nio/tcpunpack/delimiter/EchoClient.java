package com.lazycece.netty.practice.nio.tcpunpack.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

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
            ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes(StandardCharsets.UTF_8));
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new EchoClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient().connect("127.0.0.1", 8080);
    }
}
