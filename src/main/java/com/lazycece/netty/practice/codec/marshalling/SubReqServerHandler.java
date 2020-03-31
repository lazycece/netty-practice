package com.lazycece.netty.practice.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lazycece
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active: server");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("lazycece".equalsIgnoreCase(req.getUserName())) {
            System.out.println("receive msg: " + req.toString());
            SubscribeResp resp = new SubscribeResp();
            resp.setSubReqId(resp.getSubReqId());
            resp.setRespCode(200);
            resp.setDesc("hello, lazzycece");
            ctx.writeAndFlush(resp);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
