package com.weijian.game.poker.spot21.service;

import com.alibaba.fastjson.JSONObject;
import com.weijian.netty.demo.timeServer.model.Poker;

import java.util.LinkedList;
import java.util.List;

/**
 * <p> @Description 扑克操作
 *
 * @author weijian
 * @date 2020-09-10 20:57
 */

public class PokerService {

    public List<Poker> createPoker() {
        List<Poker> list = new LinkedList<>();
        String[] colors = {"黑桃", "红桃", "梅花", "方块"};
        String[] nums = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        Float[] values = {1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 0.5F, 0.5F, 0.5F};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                list.add(new Poker(nums[j], colors[i], values[j]));
            }
        }

        list.add(new Poker("大王", "大王", 0.5F));
        list.add(new Poker("小王", "小王", 0.5F));
        return list;
    }

    public static void main(String[] str) {
        System.out.println(JSONObject.toJSONString(new PokerService().createPoker()));
    }

}
