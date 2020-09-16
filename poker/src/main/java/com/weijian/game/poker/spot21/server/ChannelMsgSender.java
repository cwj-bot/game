package com.weijian.game.poker.spot21.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * <p> @Description
 *
 * @author weijian
 */

public class ChannelMsgSender {

    public static void send(ChannelHandlerContext ctx, String msg) {
        ByteBuf resp = Unpooled.copiedBuffer((msg + System.getProperty("line.separator")).getBytes());
        ctx.writeAndFlush(resp);
    }
}
