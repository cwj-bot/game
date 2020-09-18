package com.weijian.game.poker.spot21.model;

import com.weijian.game.poker.model.Poker;
import com.weijian.game.poker.util.PokerTools;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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

    private List<Poker> ownPokers;
    /**
     * 牌总值
     */
    @Builder.Default
    private Float pokerValue = 0f;
    /**
     * 状态 未准备 0, 准备 1, 可要牌 2, 不可要牌 3, 结束 4
     */
    @Builder.Default
    private Integer status = 0;
    /**
     * 身份 1 庄家  2普通人
     */
    @Builder.Default
    private Integer identity = 2;

    public Float getPokerValue() {
       return PokerTools.calculationValues(ownPokers);
    }

}
