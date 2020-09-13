package com.weijian.game.poker.spot21.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static com.weijian.game.poker.PokerApplication.context;
/**
 * <p> @Description
 *
 * @author weijian
 */

public class Spot21ServiceHandler extends ChannelInboundHandlerAdapter {

    private static Spot21Server spot21Server = (Spot21Server) context.getBean("spot21Server");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        String body = new String(req, "UTF-8");
        System.out.println("server receive info :" + body);



        System.out.println("Spot21ServiceHandler channelRead threadId" + Thread.currentThread().getId());

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
