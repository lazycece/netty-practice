package com.lazycece.netty.practice.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

/**
 * @author lazycece
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active: client");
        for (int i = 0; i < 10; ++i) {
            SubscribeReq req = new SubscribeReq();
            req.setSubReqId(i);
            req.setUserName("lazycece");
            req.setProductName("book");
            req.setAddress(Arrays.asList("Address1", "Address2", "Address3"));
            ctx.write(req);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive msg: " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
