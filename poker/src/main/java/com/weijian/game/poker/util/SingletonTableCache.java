package com.weijian.game.poker.util;

import com.weijian.game.poker.spot21.model.Table;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> @Description 单例table存储
 *
 * @author weijian
 */

public class SingletonTableCache {

    private AtomicInteger id = new AtomicInteger(100);

    private Map<Integer, Table> tableMap = new ConcurrentHashMap<>(64);

    private static class SingletonClassInstance {
        private static final SingletonTableCache instance = new SingletonTableCache();
    }

    private SingletonTableCache() {
    }

    public static SingletonTableCache getInstance() {
        return SingletonClassInstance.instance;
    }

    public Integer getId() {
        return id.addAndGet(1);
    }

    public void addTable(Table table) {
        tableMap.put(table.getTableId(), table);
    }

    public Table getTable(Integer tableId) {
        return tableMap.get(tableId);
    }
}
