package com.weijian.game.poker.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> @Description 单例Channel存储
 *
 * @author weijian
 */

public class SingletonChannelCache {

    private Map<String, Channel> channelMap = new ConcurrentHashMap<>(64);

    private static class SingletonClassInstance {
        private static final SingletonChannelCache instance = new SingletonChannelCache();
    }

    private SingletonChannelCache() {
    }

    public static SingletonChannelCache getInstance() {
        return SingletonClassInstance.instance;
    }

    public void add(String key, Channel channel) {
        channelMap.put(key, channel);
    }

    public Channel get(String key) {
        return channelMap.get(key);
    }
}
