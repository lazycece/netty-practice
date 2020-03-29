package com.lazycece.netty.practice.tcpunpack.line;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author lazycece
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private static int counter = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active: server");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("receive msg: " + body + ", counter = " + ++counter);
        String respBody = "NOW_TIME".equalsIgnoreCase(body) ? new Date().toString() : "BAD_REQ";
        respBody = respBody + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(respBody.getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
