package com.weijian.game.poker.spot21.service;

import com.weijian.game.poker.service.TableService;
import com.weijian.game.poker.spot21.model.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

            }
        }
        return table;
    }
}
