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

    private int counter;

    private byte[] req;


    public TimeClientHandler() {
        req = ("hello" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //拿到的msg已经是解码成字符串之后的应答消息了。
        String body = (String) msg;
        System.out.println(body + ++counter);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }
}
