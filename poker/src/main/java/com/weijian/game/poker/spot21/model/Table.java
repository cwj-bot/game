package com.weijian.game.poker.spot21.model;

import com.weijian.game.poker.model.Pokers;
import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * <p> @Description 桌
 *
 * @author weijian
 */
@Data
@Builder
public class Table {

    private Integer tableId;

    private Integer playerNum;

    private Integer nowPlayerNum;

    private Pokers pokers;

    private Integer status;

    private List<Player> players;

    private Integer mainPlayerId;
}
