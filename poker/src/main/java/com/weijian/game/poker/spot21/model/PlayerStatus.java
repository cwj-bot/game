package com.weijian.game.poker.spot21.model;

import lombok.AllArgsConstructor;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-09-18 18:23
 */
@AllArgsConstructor
public enum PlayerStatus {

    NO_PREPARE(0, "未准备"),
    PREPARE(1, "准备"),
    ENABLE(2, "可要牌"),
    DISABLE(3, "不可要牌"),
    END(4, "结束");

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
