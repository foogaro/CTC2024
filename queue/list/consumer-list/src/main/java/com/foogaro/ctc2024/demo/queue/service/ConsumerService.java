package com.foogaro.ctc2024.demo.queue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class ConsumerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    public Object consume(String key) {
        return consume(key, 1).getFirst();
    }

    public List<String> consume(String key, int count) {
        logger.info("Consuming\n\tlist: {}\n\tcount: {}", key, count);
        List<String> rpopped = jedis.rpop(key, count);
        logger.info("Consumed\n\tlist: {}\n\tcount: {}\n\tpopped: {}", key, count, rpopped);
        return rpopped;
    }

    public String consumeDLQ(String srcList, String destList) {
        logger.info("Consuming\n\tsrcList: {}\n\tdestList: {}", srcList, destList);
        String rpopped = jedis.rpoplpush(srcList, destList);
        logger.info("Consumed\n\tsrcList: {}\n\tdestList: {}\n\tpopped: {}", srcList, destList, rpopped);
        return rpopped;
    }

    public boolean deleteDLQ(String list, int count, String element) {
        logger.info("Deleting\n\tlist: {}\n\tcount: {}", list, count, element);
        long deleted = jedis.lrem(list, count, element);
        if (deleted > count) logger.warn("Deleted more elements than required! Required: {} - Deleted: {}", count, deleted);
        if (deleted < count) logger.warn("Deleted less elements than required! Required: {} - Deleted: {}", count, deleted);
        return (deleted == count);
    }

}
