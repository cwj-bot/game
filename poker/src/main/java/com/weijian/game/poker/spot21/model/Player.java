package com.weijian.game.poker.spot21.model;

import com.weijian.game.poker.model.Pokers;
import lombok.Builder;
import lombok.Data;

/**
 * <p> @Description  玩家
 *
 * @author weijian
 */
@Data
@Builder
public class Player {

    private Integer playerId;

    private String playerName;

    private Pokers ownPokers;
    /**
     * 牌总值
     */
    private Float pokerValue;
    /**
     * 状态 可要牌 1 不可要牌 0
     */
    private Boolean status;
    /**
     * 身份 1 庄家  2普通人
     */
    private Integer identity;
}
