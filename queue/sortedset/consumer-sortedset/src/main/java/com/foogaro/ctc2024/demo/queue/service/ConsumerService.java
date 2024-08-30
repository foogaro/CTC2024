package com.foogaro.ctc2024.demo.queue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.List;

@Service
public class ConsumerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    public Tuple consumeMin(String key) {
        return consumeMin(key, 1).getFirst();
    }

    public List<Tuple> consumeMin(String key, int count) {
        logger.info("Consuming\n\tsortedset: {}\n\tcount: {}", key, count);
        List<Tuple> rpopped = jedis.zpopmin(key, count);
        logger.info("Consumed\n\tsortedset: {}\n\tcount: {}\n\tpopped: {}", key, count, rpopped);
        return rpopped;
    }

    public Tuple consumeMax(String key) {
        return consumeMax(key, 1).getFirst();
    }

    public List<Tuple> consumeMax(String key, int count) {
        logger.info("Consuming\n\tsortedset: {}\n\tcount: {}", key, count);
        List<Tuple> rpopped = jedis.zpopmax(key, count);
        logger.info("Consumed\n\tsortedset: {}\n\tcount: {}\n\tpopped: {}", key, count, rpopped);
        return rpopped;
    }

}
