package com.foogaro.ctc2024.demo.queue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class ProducerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    public Long produce(String key, double score, Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(object);
            return produce(key, score, json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Long produce(String key, double score, String message) {
        logger.info("Producing\n\tkey: {}\n\tscore: {}\n\tmessage: {}", key, score, message);
        return jedis.zadd(key, score, message);
    }

}
