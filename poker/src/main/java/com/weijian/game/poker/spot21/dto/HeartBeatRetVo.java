package com.weijian.game.poker.spot21.dto;

import com.weijian.game.poker.spot21.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> @Description
 *
 * @author weijian
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartBeatRetVo extends RetVo {

    private Player player;
}
