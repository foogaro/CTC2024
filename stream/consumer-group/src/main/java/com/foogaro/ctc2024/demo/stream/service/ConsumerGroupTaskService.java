package com.foogaro.ctc2024.demo.stream.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.function.Function;

@Service
public class ConsumerGroupTaskService implements Function<Map<?, ?>, Long> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    /*
    xreadGroup(String groupName, String consumer,
               XReadGroupParams xReadGroupParams,
               Map<String, StreamEntryID> streams)

     */
    @Override
    public Long apply(Map<?, ?> map) {

        return null;
    }
}
