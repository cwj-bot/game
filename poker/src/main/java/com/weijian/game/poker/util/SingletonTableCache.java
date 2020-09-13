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

    private AtomicInteger tableId = new AtomicInteger(100);
    private AtomicInteger playerId = new AtomicInteger(10000);

    private Map<Integer, Table> tableMap = new ConcurrentHashMap<>(64);

    private static class SingletonClassInstance {
        private static final SingletonTableCache instance = new SingletonTableCache();
    }

    private SingletonTableCache() {
    }

    public static SingletonTableCache getInstance() {
        return SingletonClassInstance.instance;
    }

    public Integer getTableId() {
        return tableId.addAndGet(1);
    }

    public Integer getPlayerId() {
        return tableId.addAndGet(1);
    }

    public void addTable(Table table) {
        tableMap.put(table.getTableId(), table);
    }

    public Table getTable(Integer tableId) {
        return tableMap.get(tableId);
    }
}
