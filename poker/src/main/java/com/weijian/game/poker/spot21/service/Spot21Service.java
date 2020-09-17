package com.weijian.game.poker.spot21.service;

import com.weijian.game.poker.spot21.dto.*;
import com.weijian.game.poker.spot21.exception.SystemException;
import com.weijian.game.poker.spot21.model.Player;
import com.weijian.game.poker.spot21.model.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


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
    public CreateJoinTableRetVo createTable(Integer userNum, String playerName) {
        Player player = playerService.createPlayer(playerName, 1);
        Table table = tableService.createTable(userNum, player);
        CreateJoinTableRetVo ret = new CreateJoinTableRetVo();
        ret.setIdentify(1);
        ret.setNowPlayerNum(table.getNowPlayerNum());
        ret.setPlayerId(player.getPlayerId());
        ret.setPlayerNum(table.getPlayerNum());
        ret.setTableId(table.getTableId());
        ret.setStatus(table.getStatus());
        return ret;
    }

    /**
     * 加入桌
     * @param tableId
     * @param playerName
     * @return
     */
    public CreateJoinTableRetVo joinTable(Integer tableId, String playerName) {
        Player player = playerService.createPlayer(playerName, 2);
        Table table = tableService.joinTable(player, tableId);
        checkTable(table);
        CreateJoinTableRetVo ret = new CreateJoinTableRetVo();
        ret.setIdentify(2);
        ret.setNowPlayerNum(table.getNowPlayerNum());
        ret.setPlayerId(player.getPlayerId());
        ret.setPlayerNum(table.getPlayerNum());
        ret.setTableId(table.getTableId());
        return ret;
    }

    /**
     * 开始
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
        return ret;
    }

    /**
     * 开盘
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
     * @param playerId
     * @param tableId
     * @return
     */
    public TakePokerRet pass(Integer playerId, Integer tableId) {
        tableService.pass(playerId, tableId);
        return new TakePokerRet();
    }


    private void checkTable(Table table) {
        if (table == null) {
            throw new SystemException("桌不存在");
        }
    }

}
