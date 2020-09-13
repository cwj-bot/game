package com.weijian.game.poker.spot21.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-09-13 12:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpeningTableRetVo extends RetVo{

    private Integer tableId;

    private Integer playerNum;

    private Integer nowPlayerNum;

    private Integer status;
}
