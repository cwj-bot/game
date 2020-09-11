package com.weijian.game.poker.spot21.service;

import com.google.common.collect.Lists;
import com.weijian.game.poker.model.Poker;
import com.weijian.game.poker.model.Pokers;
import com.weijian.game.poker.spot21.model.Player;
import com.weijian.game.poker.spot21.model.Table;
import com.weijian.game.poker.util.SingletonTableCache;
import org.springframework.stereotype.Service;


/**
 * <p> @Description
 *
 * @author weijian
 */

@Service
public class Spot21Service {

    public Integer createTable(Integer userNum, Player player) {
        // TODO userNum check;
        Integer tableId = SingletonTableCache.getInstance().getId();
        Table table = Table.builder().tableId(SingletonTableCache.getInstance().getId())
                .pokers(new Pokers())
                .players(Lists.newArrayList(player))
                .status(0)
                .playerNum(userNum)
                .nowPlayerNum(1)
                .mainPlayerId(player.getPlayerId())
                .build();

        SingletonTableCache.getInstance().addTable(table);
        return tableId;
    }

    public Table joinTable(Player player, Integer tableId) {
        Table table = getTable(tableId);
        // TODO check
        if (table != null) {
            if (table.getPlayerNum() > table.getNowPlayerNum()) {
                table.setNowPlayerNum(table.getNowPlayerNum() + 1);
                return table;
            }
        }
        return null;
    }


    public Table opening(Integer playerId, Integer tableId) {
        Table table = getTable(tableId);
        if (table != null) {
            if (tableId.equals(table.getMainPlayerId())) {

            }
        }
        return table;
    }


    private Table getTable(Integer tableId) {
        return SingletonTableCache.getInstance().getTable(tableId);
    }
}
