package com.weijian.game.poker.spot21.server;

import com.alibaba.fastjson.JSONObject;
import com.weijian.game.poker.spot21.dto.CreateJoinTableRetVo;
import com.weijian.game.poker.spot21.dto.InterFaceMsgReq;
import com.weijian.game.poker.spot21.service.Spot21Service;
import com.weijian.game.poker.util.Constant;
import com.weijian.game.poker.util.SingletonChannelCache;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
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
                check(playerId, tableId);
                ChannelMsgSender.send(ctx,  spot21Service.openingTable(playerId, tableId, 2), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_OPEN_4: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                check(playerId, tableId);
                ChannelMsgSender.send(ctx,  spot21Service.openTable(playerId, tableId), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_PREPARE_5: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                check(playerId, tableId);
                ChannelMsgSender.send(ctx,  spot21Service.prepare(playerId, tableId), interfaceCode);
            }
            break;
            case Constant.INTERFACE_21_SPOT_GET_POKER_6: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                check(playerId, tableId);
                ChannelMsgSender.send(ctx,  spot21Service.takePoker(playerId, tableId), interfaceCode);
                spot21Service.pushNextPlayer(playerId, tableId);
            }
            break;
            case Constant.INTERFACE_21_SPOT_PASS_7: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                check(playerId, tableId);
                ChannelMsgSender.send(ctx,  spot21Service.pass(playerId, tableId), interfaceCode);
                spot21Service.pushNextPlayer(playerId, tableId);
            }
            break;
            case Constant.INTERFACE_21_SPOT_CLIENT_SERVER_8: {
                Integer playerId = interFaceMsgReq.getPlayerId();
                Integer tableId = interFaceMsgReq.getTableId();
                check(playerId, tableId);
                spot21Service.heartBeat(ctx.channel(), playerId, tableId);
            }
            break;
            default: {

            }
        }
    }


    private void check(Integer playerId, Integer tableId) {
        Assert.notNull(playerId, "玩家ID不能为空");
        Assert.notNull(tableId, "桌号不能为空");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.write(Unpooled.copiedBuffer("channelActive".getBytes()));
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        String playerKey = SingletonChannelCache.getInstance().get(ctx.channel());
        log.debug("userEventTriggered channelId = {} playerKey = {} ", ctx.channel().id(), playerKey);
        spot21Service.offline(playerKey);
        SingletonChannelCache.getInstance().remove(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String playerKey = SingletonChannelCache.getInstance().get(ctx.channel());
            switch (event.state()) {
                case READER_IDLE: {
                    log.debug("userEventTriggered channelId = {} playerKey = {} ", ctx.channel().id(), playerKey);
                    spot21Service.offline(playerKey);
                    SingletonChannelCache.getInstance().remove(ctx.channel());
                    if (ctx.channel().isActive()) {
                        ctx.channel().disconnect();
                    }
                }
                break;
                case WRITER_IDLE:
                    log.trace("No data was sent for a while");
                    break;
                case ALL_IDLE:
                    log.trace("No data was either received or sent for a while");
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
