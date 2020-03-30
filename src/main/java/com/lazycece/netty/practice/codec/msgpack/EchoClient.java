package com.lazycece.netty.practice.codec.msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

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
            ch.pipeline().addLast("frame_decoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
            ch.pipeline().addLast("msgpack_decoder", new MsgpackDecoder());
            ch.pipeline().addLast("frame_encoder", new LengthFieldPrepender(2));
            ch.pipeline().addLast("msgpack_encoder", new MsgpackEncoder());
            ch.pipeline().addLast(new EchoClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient().connect("127.0.0.1", 8080);
    }
}
