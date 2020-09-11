package com.weijian.game.poker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p> @Description 扑克
 *
 * @author weijian
 */
@Data
@AllArgsConstructor
public class Poker {
    /**
     * 面值
     */
    private String code;
    /**
     * 花色
     */
    private String color;
    /**
     * 点数
     */
    private Float value;

}
