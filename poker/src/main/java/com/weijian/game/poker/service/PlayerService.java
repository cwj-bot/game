package com.weijian.game.poker.service;

import com.weijian.game.poker.spot21.model.Player;
import com.weijian.game.poker.util.SingletonTableCache;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Description 玩家服务
 *
 * @author weijian
 * @date 2020-09-13 12:08
 */

public class PlayerService {

    public Player createPlayer(String playerName, Integer identity) {
        return Player.builder().playerId(SingletonTableCache.getInstance().getPlayerId())
                .playerName(playerName).identity(identity).build();
    }
}
