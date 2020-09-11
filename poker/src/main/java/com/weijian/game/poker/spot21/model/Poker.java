package com.weijian.game.poker.spot21.model;

/**
 * <p> @Description 扑克
 *
 * @author weijian
 * @date 2020-09-10 20:53
 */

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

    public Poker(String code, String color, Float value) {
        this.code = code;
        this.color = color;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
