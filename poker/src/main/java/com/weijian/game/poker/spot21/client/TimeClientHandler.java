package com.weijian.game.poker.spot21.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-07-15 19:06
 */

public class TimeClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        System.out.println("time info :" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        byte[] req = "time".getBytes();
        ByteBuf msg = Unpooled.buffer(req.length);
        msg.writeBytes(req);
        ctx.writeAndFlush(msg);
    }
}
