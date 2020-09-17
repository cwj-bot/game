package com.weijian.game.poker.spot21.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * <p> @Description
 *
 * @author weijian
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterFaceMsgReq {

    @NonNull
    private String interfaceType;

    private String playerName;

    private Integer playerNum;

    private Integer tableId;

    private Integer playerId;

}