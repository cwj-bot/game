package com.weijian.game.poker.spot21.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-07-15 17:45
 */

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new Spot21ServiceHandler());
    }
}
