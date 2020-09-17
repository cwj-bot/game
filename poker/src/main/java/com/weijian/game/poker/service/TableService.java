package com.weijian.game.poker.service;

import com.google.common.collect.Lists;
import com.weijian.game.poker.model.Poker;
import com.weijian.game.poker.model.Pokers;
import com.weijian.game.poker.spot21.exception.SystemException;
import com.weijian.game.poker.spot21.model.Player;
import com.weijian.game.poker.spot21.model.Table;
import com.weijian.game.poker.util.PokerTools;
import com.weijian.game.poker.util.SingletonTableCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * <p> @Description
 *
 * @author weijian
 */

@Slf4j
public abstract class TableService {

    private final static int USER_NUM_LIMIT = 8;

    public Table createTable(Integer userNum, Player player) {
        if (userNum < 0 || userNum > USER_NUM_LIMIT) {
            throw new IllegalArgumentException("每桌玩家数最多8人");
        }
        Integer tableId = SingletonTableCache.getInstance().getTableId();
        Table table = Table.builder().tableId(tableId)
                .players(Lists.newArrayList(player))
                .status(0)
                .playerNum(userNum)
                .nowPlayerNum(1)
                .mainPlayerId(player.getPlayerId())
                .build();

        SingletonTableCache.getInstance().addTable(table);
        return table;
    }

    public Table joinTable(Player player, Integer tableId) {
        Table table = getTable(tableId);
        if (table == null) {
            return null;
        }
        if (table.getStatus() == 0) {
            if (table.getPlayerNum() > table.getNowPlayerNum()) {
                table.setNowPlayerNum(table.getNowPlayerNum() + 1);
                List<Player> players = table.getPlayers();
                players.add(player);
                return table;
            }
        } else {
            throw new IllegalArgumentException("不能参加已经开始的桌，请等待本场次结束。");
        }
        return null;
    }

    // 开场
    public Table opening(Integer playerId, Integer tableId, Integer num) {
        Table table = getTable(tableId);
        if (table != null) {
            // 庄家 并且都准备
            if (playerId.equals(table.getMainPlayerId()) && checkPrepare(table.getPlayers(), 1)) {
                Pokers pokers = new Pokers();
                table.setPokers(pokers);
                // 每人发两张牌
                List<Player> players = table.getPlayers();
                PokerTools.shuffle(pokers); // 洗牌
                // 发牌
                for (int i = 0; i < num; i++) {
                    for (Player player : players) {

                        List<Poker> playerPokers = pokers.getPokers();
                        if (CollectionUtils.isEmpty(playerPokers)) {
                            playerPokers = Lists.newArrayList();
                        }
                        Poker poker = PokerTools.take(pokers);
                        if (poker == null) {
                            throw new SystemException("扑克牌发完了");
                        }
                        playerPokers.add(poker);
                        player.setOwnPokers(playerPokers);
                    }
                }

                table.setStatus(1); // 开始
            }
        }
        return table;
    }

    // 开盘结算
    protected abstract Table openTable(Integer playerId, Integer tableId);

    // 抓牌
    protected abstract Table takePoker(Integer playerId, Integer tableId, Integer num);

    public Boolean prepare(Integer playerId, Integer tableId) {
        return updatePlayerStatus(playerId, tableId, 1);
    }


    public Boolean pass(Integer playerId, Integer tableId) {
        return updatePlayerStatus(playerId, tableId, 3);
    }


    private Boolean updatePlayerStatus(Integer playerId, Integer tableId, Integer status) {
        Table table = getTable(tableId);
        if (table == null) {
            throw new SystemException("桌不存在");
        }
        List<Player> players = table.getPlayers();
        for (Player player : players) {
            if (playerId.equals(player.getPlayerId())) {
                player.setStatus(status);
                return true;
            }
        }
        return false;
    }

    // 校验状态
    protected Boolean checkPrepare(List<Player> players, int status) {
        for (Player player : players) {
            if (player.getStatus() != status) {
                return false;
            }
        }
        return true;
    }


    protected Table getTable(Integer tableId) {
        return SingletonTableCache.getInstance().getTable(tableId);
    }
}
