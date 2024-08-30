package com.foogaro.ctc2024.demo.stream.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProducerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    public String produce(String stream, Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = om.writeValueAsString(object);
            Map<String, String> map = new HashMap<>();
            map.put("json", json);
            logger.info("Producing\n\tstream: {}\n\tobject: {}", stream, map);
            return jedis.xadd(stream, StreamEntryID.NEW_ENTRY, map).toString();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
