package com.weijian.game.poker.spot21.service;

import com.weijian.game.poker.model.Poker;
import com.weijian.game.poker.model.Pokers;
import com.weijian.game.poker.service.TableService;
import com.weijian.game.poker.spot21.exception.SystemException;
import com.weijian.game.poker.spot21.model.Player;
import com.weijian.game.poker.spot21.model.Table;
import com.weijian.game.poker.util.PokerTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-09-13 13:02
 */
@Service
@Slf4j
public class Spot21TableService extends TableService {

    @Override
    public Table openTable(Integer playerId, Integer tableId) {
        Table table = super.getTable(tableId);
        if (table != null) {
            // 庄家 并且都不在要牌
            if (tableId.equals(table.getMainPlayerId()) && super.checkPrepare(table.getPlayers(), 3)) {
                table.setStatus(0);
                return table;
            } else {
                throw new SystemException("还有人未选择pass");
            }
        }
        return null;
    }

    @Override
    protected Table takePoker(Integer playerId, Integer tableId, Integer num) {
        Table table = super.getTable(tableId);
        if (table != null) {
            List<Player> players = table.getPlayers();
            Pokers pokers = table.getPokers();
            // 发牌
            for (int i = 0; i < num; i++) {
                for (Player player : players) {
                    if (playerId.equals(player.getPlayerId()) && player.getStatus() == 2) {
                        List<Poker> playerPokers = pokers.getPokers();
                        Poker poker = PokerTools.take(pokers);
                        playerPokers.add(poker);
                        player.setOwnPokers(playerPokers);
                    }
                }
            }
        }
        return table;
    }

}
