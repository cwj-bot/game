package com.weijian.game.poker.spot21.dto;

import lombok.*;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-09-13 12:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJoinTableRetVo extends RetVo{

    private Integer tableId;

    private Integer playerId;

    private Integer identify;

    private Integer playerNum;

    private Integer nowPlayerNum;

    private Integer status;
}
