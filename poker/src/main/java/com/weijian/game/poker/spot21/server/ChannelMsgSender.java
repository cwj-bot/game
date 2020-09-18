package com.weijian.game.poker.spot21.server;

import com.alibaba.fastjson.JSONObject;
import com.weijian.game.poker.spot21.dto.RetVo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Description
 *
 * @author weijian
 */

@Slf4j
public class ChannelMsgSender {

    public static void send(ChannelHandlerContext ctx, RetVo body, String interfaceType) {
        body.setInterfaceType(interfaceType);
        String msg =  JSONObject.toJSONString(body);
        log.info("ctx send msg:{}", msg);
        ByteBuf resp = Unpooled.copiedBuffer((msg + System.getProperty("line.separator")).getBytes());
        ctx.writeAndFlush(resp);
    }

    public static void send(Channel channel, RetVo body, String interfaceType) {
        body.setInterfaceType(interfaceType);
        String msg =  JSONObject.toJSONString(body);
        log.info("channel send msg:{}", msg);
        ByteBuf resp = Unpooled.copiedBuffer((msg + System.getProperty("line.separator")).getBytes());
        channel.writeAndFlush(resp);
    }
}
