package com.weijian.game.poker.spot21.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.weijian.game.poker.spot21.service.Spot21Service;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;


import java.util.Map;

import static com.weijian.game.poker.PokerApplication.context;
/**
 * <p> @Description
 *
 * @author weijian
 */

@Slf4j
public class Spot21ServiceHandler extends ChannelInboundHandlerAdapter {

    private static final Spot21Service spot21Service = (Spot21Service) context.getBean("spot21Service");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server channelRead info :" + msg);
        Map<String, Object> reqMap = Maps.newHashMap();
        try {
            String body = (String)msg;
            reqMap = JSONObject.parseObject(body.trim());
        } catch (Exception e) {
            log.error("msg = {}, json error: ", msg, e);
            return;
        }
        String interfaceCode = MapUtils.getString(reqMap, "interfaceCode");

//        ChannelMsgSender.send(ctx,  body + " world");
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
