package com.foogaro.ctc2024.demo.pubsub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class PubService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    public Long publish(String channel, Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(object);
            return publish(channel, json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Long publish(String channel, String message) {
        logger.info("Publishing\n\tchannel: {}\n\tmessage: {}", channel, message);
        return jedis.publish(channel, message);
    }

}
