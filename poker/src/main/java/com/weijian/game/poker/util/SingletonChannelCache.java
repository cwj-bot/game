package com.weijian.game.poker.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> @Description 单例Channel存储
 *
 * @author weijian
 */

public class SingletonChannelCache {

    private static Map<String, Channel> playerChannelMap = new ConcurrentHashMap<>(64);
    private static Map<ChannelId, String> channelPlayerMap = new ConcurrentHashMap<>(64);

    private static class SingletonClassInstance {
        private static final SingletonChannelCache instance = new SingletonChannelCache();
    }

    private SingletonChannelCache() {
    }

    public static SingletonChannelCache getInstance() {
        return SingletonClassInstance.instance;
    }

    public void add(String key, Channel channel) {
        playerChannelMap.put(key, channel);
        channelPlayerMap.put(channel.id(), key);
    }

    public Channel get(String key) {
        return playerChannelMap.get(key);
    }

    public String get(Channel channel) {
        return channelPlayerMap.get(channel.id());
    }

    public void remove(String key) {
        Channel channel = get(key);
        if (key != null) {
            playerChannelMap.remove(key);
            channelPlayerMap.remove(channel.id());
        }
    }

    public void remove(Channel channel) {
        String key = get(channel);
        if (key != null) {
            playerChannelMap.remove(key);
            channelPlayerMap.remove(channel.id());
        }
    }

}
