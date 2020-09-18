package com.weijian.game.poker.spot21.service;

import com.weijian.game.poker.spot21.dto.*;
import com.weijian.game.poker.spot21.exception.SystemException;
import com.weijian.game.poker.spot21.model.Player;
import com.weijian.game.poker.spot21.model.PlayerStatus;
import com.weijian.game.poker.spot21.model.Table;
import com.weijian.game.poker.spot21.server.ChannelMsgSender;
import com.weijian.game.poker.util.Constant;
import com.weijian.game.poker.util.SingletonChannelCache;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * <p> @Description
 *
 * @author weijian
 */

@Service
@Slf4j
public class Spot21Service {

    @Resource
    private Spot21PlayerService playerService;

    @Resource
    private Spot21TableService tableService;

    /**
     * 创建桌
     *
     * @param userNum
     * @param playerName
     * @return
     */
    public CreateJoinTableRetVo createTable(Integer userNum, String playerName, Channel channel) {
        Player player = playerService.createPlayer(playerName, 1);
        Table table = tableService.createTable(userNum, player);
        CreateJoinTableRetVo ret = new CreateJoinTableRetVo();
        ret.setIdentify(1);
        ret.setNowPlayerNum(table.getNowPlayerNum());
        ret.setPlayerId(player.getPlayerId());
        ret.setPlayerNum(table.getPlayerNum());
        ret.setTableId(table.getTableId());
        ret.setStatus(table.getStatus());
        bindChannel(channel, key(table.getTableId(), player.getPlayerId()));
        return ret;
    }

    private void bindChannel(Channel channel, String key) {
        if (channel.isActive()) {
            SingletonChannelCache.getInstance().add(key, channel);
        }
    }

    /**
     * 加入桌
     *
     * @param tableId
     * @param playerName
     * @return
     */
    public CreateJoinTableRetVo joinTable(Integer tableId, String playerName, Channel channel) {
        Player player = playerService.createPlayer(playerName, 2);
        Table table = tableService.joinTable(player, tableId);
        checkTable(table);
        CreateJoinTableRetVo ret = new CreateJoinTableRetVo();
        ret.setIdentify(2);
        ret.setNowPlayerNum(table.getNowPlayerNum());
        ret.setPlayerNum(table.getPlayerNum());
        ret.setPlayerId(player.getPlayerId());
        ret.setTableId(table.getTableId());
        bindChannel(channel, key(table.getTableId(), player.getPlayerId()));
        return ret;
    }

    /**
     * 开始
     *
     * @param playerId
     * @param tableId
     * @param num
     * @return
     */
    public OpeningTableRetVo openingTable(Integer playerId, Integer tableId, Integer num) {
        Table table = tableService.opening(playerId, tableId, num);
        checkTable(table);
        OpeningTableRetVo ret = new OpeningTableRetVo();
        ret.setNowPlayerNum(table.getNowPlayerNum());
        ret.setPlayerNum(table.getPlayerNum());
        ret.setTableId(table.getTableId());
        ret.setStatus(table.getStatus());

        List<Player> players = table.getPlayers();
        for (Player player : players) {
            if (player.getPlayerId().equals(playerId)) {
                player.setStatus(PlayerStatus.ENABLE.getCode());
            } else {
                player.setStatus(PlayerStatus.DISABLE.getCode());
            }
            pushPlayerInfo(player, tableId);
        }
        return ret;
    }

    /**
     * 开盘
     *
     * @param playerId
     * @param tableId
     * @return
     */
    public OpenTableRet openTable(Integer playerId, Integer tableId) {
        Table table = tableService.openTable(playerId, tableId);
        checkTable(table);
        OpenTableRet ret = new OpenTableRet();
        ret.setTable(table);
        return ret;
    }

    /**
     * 玩家准备
     *
     * @param playerId
     * @param tableId
     * @return
     */
    public PrepareRet prepare(Integer playerId, Integer tableId) {
        PrepareRet ret = new PrepareRet();
        ret.setResult(tableService.prepare(playerId, tableId));
        return ret;
    }

    /**
     * 发牌
     *
     * @param playerId
     * @param tableId
     * @return
     */
    public TakePokerRet takePoker(Integer playerId, Integer tableId) {
        tableService.takePoker(playerId, tableId, 1);
        return new TakePokerRet();
    }

    /**
     * 过
     *
     * @param playerId
     * @param tableId
     * @return
     */
    public TakePokerRet pass(Integer playerId, Integer tableId) {
        tableService.pass(playerId, tableId);
        return new TakePokerRet();
    }


    private void pushPlayerInfo(Player player, Integer tableId) {
        Channel channel = SingletonChannelCache.getInstance().get(key(tableId, player.getPlayerId()));
        ChannelMsgSender.send(channel, new HeartBeatRetVo(player), Constant.INTERFACE_21_SPOT_CLIENT_SERVER_8);
    }

    public void pushPlayerInfo(Channel channel, Integer playerId, Integer tableId) {
        Player player = tableService.getPlayer(playerId, tableId);
        ChannelMsgSender.send(channel, new HeartBeatRetVo(player), Constant.INTERFACE_21_SPOT_CLIENT_SERVER_8);
    }


    public void pushNextPlayer(Integer playerId, Integer tableId) {
        Table table = tableService.getTable(tableId);
        LinkedList<Player> players = table.getPlayers();
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getPlayerId().equals(playerId)) {
                pushPlayerInfo(player, tableId);
                while (iterator.hasNext()) {
                    Player next = iterator.next();
                    if (next.getStatus() != PlayerStatus.END.getCode()) {
                        next.setStatus(PlayerStatus.ENABLE.getCode()); // 可要
                        pushPlayerInfo(next, tableId);
                        return;
                    }
                }
            }
        }

        for (Player next : players) {
            if (next.getStatus() != PlayerStatus.END.getCode()) {
                next.setStatus(PlayerStatus.ENABLE.getCode()); // 可要
                pushPlayerInfo(next, tableId);
                return;
            }
        }
    }


    private String key(Integer tableId, Integer playerId) {
        return tableId + "_" + playerId;
    }


    private void checkTable(Table table) {
        if (table == null) {
            throw new SystemException("桌不存在");
        }
    }

}
