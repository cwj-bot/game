package com.weijian.game.poker.spot21.server;

import com.alibaba.fastjson.JSONObject;
import com.weijian.game.poker.spot21.dto.CreateJoinTableRetVo;
import com.weijian.game.poker.spot21.dto.InterFaceMsgReq;
import com.weijian.game.poker.spot21.service.Spot21Service;
import com.weijian.game.poker.util.Constant;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;


import static com.weijian.game.poker.PokerApplication.context;

/**
 * <p> @Description 21点handler
 *
 * @author weijian
 */

@Slf4j
public class Spot21ServiceHandler extends ChannelInboundHandlerAdapter {

    private static final Spot21Service spot21Service = (Spot21Service) context.getBean("spot21Service");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("server channelRead info :" + msg);
        try {
            String body = (String) msg;
            assignment(ctx, JSONObject.parseObject(body.trim(), InterFaceMsgReq.class));
        } catch (Exception e) {
            log.error("msg = {}, json error: ", msg, e);
        }
    }

    private void assignment(ChannelHandlerContext ctx, InterFaceMsgReq interFaceMsgReq) {
        String interfaceCode = interFaceMsgReq.getInterfaceType();

        switch (interfaceCode) {
            case Constant.INTERFACE_21_SPOT_CREATE_TABLE_1: {
                String playerName = interFaceMsgReq.getPlayerName();
                Integer playerNum = interFaceMsgReq.getPlayerNum();
                Assert.notNull(playerName, "玩家名称不能为空");
                Assert.notNull(playerNum, "玩家名称不能为空");
                ChannelMsgSender.send(ctx,  spot21Service.createTable(playerNum, playerName, ctx.channel()), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_JOIN_TABLE_2: {
                String playerName = interFaceMsgReq.getPlayerName();
                Integer tableId = interFaceMsgReq.getTableId();
                Assert.notNull(playerName, "玩家名称不能为空");
                Assert.notNull(tableId, "桌号不能为空");
                CreateJoinTableRetVo createJoinTableRetVo = spot21Service.joinTable(tableId, playerName, ctx.channel());
                ChannelMsgSender.send(ctx,  createJoinTableRetVo, interfaceCode);
                spot21Service.pushPlayerInfo(ctx.channel(), createJoinTableRetVo.getPlayerId(), tableId);
            }
            break;
            case Constant.INTERFACE_21_SPOT_OPENING_3: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                Assert.notNull(playerId, "玩家ID不能为空");
                Assert.notNull(tableId, "桌号不能为空");
                ChannelMsgSender.send(ctx,  spot21Service.openingTable(playerId, tableId, 2), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_OPEN_4: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                Assert.notNull(playerId, "玩家ID不能为空");
                Assert.notNull(tableId, "桌号不能为空");
                ChannelMsgSender.send(ctx,  spot21Service.openTable(playerId, tableId), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_PREPARE_5: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                Assert.notNull(playerId, "玩家ID不能为空");
                Assert.notNull(tableId, "桌号不能为空");
                ChannelMsgSender.send(ctx,  spot21Service.prepare(playerId, tableId), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_GET_POKER_6: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                Assert.notNull(playerId, "玩家ID不能为空");
                Assert.notNull(tableId, "桌号不能为空");
                ChannelMsgSender.send(ctx,  spot21Service.takePoker(playerId, tableId), interfaceCode);
                spot21Service.pushNextPlayer(playerId, tableId);
            }
            break;
            case Constant.INTERFACE_21_SPOT_PASS_7: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                Assert.notNull(playerId, "玩家ID不能为空");
                Assert.notNull(tableId, "桌号不能为空");
                ChannelMsgSender.send(ctx,  spot21Service.pass(playerId, tableId), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_CLIENT_SERVER_8: {
//                Integer playerId = interFaceMsgReq.getPlayerId();
//                Integer tableId = interFaceMsgReq.getTableId();
//                Assert.notNull(playerId, "玩家ID不能为空");
//                Assert.notNull(tableId, "桌号不能为空");
//                ChannelMsgSender.send(ctx,  spot21Service.(playerId, tableId), interfaceCode);
            }
            break;
            default: {

            }
        }
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
