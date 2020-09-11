package com.weijian.game.poker.util;

import com.alibaba.fastjson.JSONObject;
import com.weijian.game.poker.model.Poker;
import com.weijian.game.poker.model.Pokers;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * <p> @Description 扑克操作
 *
 * @author weijian
 * @date 2020-09-10 20:57
 */

public class PokerTools {


    public static void shuffle(Pokers pokers) {
        Collections.shuffle(pokers.getPokers());
    }

    public static Poker take(Pokers pokers) {
        if (!pokers.getPokers().isEmpty()) {
            return pokers.getPokers().pollFirst();
        }
        return null;
    }

    public static Float calculationValues(List<Poker> pokers) {
        if (!CollectionUtils.isEmpty(pokers)) {
            BigDecimal sum = BigDecimal.ZERO;
            for (Poker poker : pokers) {
                BigDecimal tmp = new BigDecimal(Double.toString(poker.getValue()));
                sum = sum.add(tmp);
            }
            return sum.floatValue();
        }
        return 0f;
    }



    public static void main(String[] str) {
        Pokers pokers = new Pokers();
        System.out.println(JSONObject.toJSONString(pokers.getPokers()));
        shuffle(pokers);
        System.out.println(JSONObject.toJSONString(pokers.getPokers()));
        for (int i = 0; i< 54; i++) {
            System.out.println(take(pokers));
            System.out.println(JSONObject.toJSONString(pokers.getPokers()));
        }

        System.out.println(calculationValues(pokers.getPokers()));

    }

}
