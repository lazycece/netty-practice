package com.lazycece.netty.practice.tcpunpack.fixedlength;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author lazycece
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private static int counter = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active: client");
        String reqMsg = "Hello netty! echo";
        byte[] req = reqMsg.getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = Unpooled.buffer(req.length);
        buf.writeBytes(req);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("receive msg: " + body + ", counter = " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exception: " + cause.getMessage());
        ctx.close();
    }
}
