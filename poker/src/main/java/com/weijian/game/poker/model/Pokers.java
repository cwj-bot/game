package com.weijian.game.poker.model;

import lombok.Data;

import java.util.LinkedList;

/**
 * <p> @Description 一副扑克
 *
 * @author weijian
 */

@Data
public class Pokers {

    private LinkedList<Poker> pokers = new LinkedList<>();

    public Pokers() {
        String[] colors = {"黑桃", "红桃", "梅花", "方块"};
        String[] nums = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        Float[] values = {1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 0.5F, 0.5F, 0.5F};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                pokers.add(new Poker(nums[j], colors[i], values[j]));
            }
        }

        pokers.add(new Poker("大王", "大王", 0.5F));
        pokers.add(new Poker("小王", "小王", 0.5F));
    }
}
