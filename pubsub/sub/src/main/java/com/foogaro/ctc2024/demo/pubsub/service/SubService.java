package com.foogaro.ctc2024.demo.pubsub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

public class SubService extends JedisPubSub {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String name;

    public SubService() {
        this("subscriber");
    }

    public SubService(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        logger.info("Received onMessage:\n\tchannel: {}\n\tmessage: {}", channel, message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        super.onPMessage(pattern, channel, message);
        logger.info("Received onPMessage:\n\tpattern: {}\n\tchannel: {}\n\tmessage: {}", pattern, channel, message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        super.onSubscribe(channel, subscribedChannels);
        logger.info("Received onSubscribe:\n\tchannel: {}\n\tsubscribedChannels: {}", channel, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        super.onUnsubscribe(channel, subscribedChannels);
        logger.info("Received onUnsubscribe:\n\tchannel: {}\n\tsubscribedChannels: {}", channel, subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        super.onPUnsubscribe(pattern, subscribedChannels);
        logger.info("Received onPUnsubscribe:\n\tpattern: {}\n\tsubscribedChannels: {}", pattern, subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        super.onPSubscribe(pattern, subscribedChannels);
        logger.info("Received onPSubscribe:\n\tpattern: {}\n\tsubscribedChannels: {}", pattern, subscribedChannels);
    }

    @Override
    public void onPong(String pattern) {
        super.onPong(pattern);
        logger.info("Received onPong:\n\tpattern: {}", pattern);
    }
}
