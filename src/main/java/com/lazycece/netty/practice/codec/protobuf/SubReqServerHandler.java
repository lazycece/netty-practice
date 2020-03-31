package com.lazycece.netty.practice.codec.protobuf;

import com.layzcece.netty.practice.codec.protobuf.SubscribeReqProto;
import com.layzcece.netty.practice.codec.protobuf.SubscribeRespProto;
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
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("lazycece".equalsIgnoreCase(req.getUserName())) {
            System.out.println("receive msg: " + req.toString());
            ctx.writeAndFlush(
                    SubscribeRespProto.SubscribeResp
                            .newBuilder()
                            .setSubReqId(req.getSubReqId())
                            .setRespCode(200)
                            .setDesc("hello, lazzycece")
                            .build()
            );
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
