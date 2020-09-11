package com.weijian.game.poker.spot21.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-07-15 17:49
 */

public class Spot21ServiceHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        String body = new String(req, "UTF-8");
        System.out.println("server receive info :" + body);

        String currentTime = "time".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "bad request";

        ByteBuf res = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(res);
        System.out.println("Spot21ServiceHandler channelRead threadId" + Thread.currentThread().getId());

        ctx.channel().eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Spot21ServiceHandler channelRead schedule threadId:" + Thread.currentThread().getId());
            }
        }, 1 , 30, TimeUnit.SECONDS);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(Unpooled.copiedBuffer("channelActive".getBytes()));
        System.out.println("Spot21ServiceHandler channelActive threadId:" + Thread.currentThread().getId());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Spot21ServiceHandler channelRegistered threadId:" + Thread.currentThread().getId());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Spot21ServiceHandler handlerAdded threadId:" + Thread.currentThread().getId());
    }


}
