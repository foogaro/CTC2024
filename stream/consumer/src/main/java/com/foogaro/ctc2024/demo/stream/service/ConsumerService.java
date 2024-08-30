package com.foogaro.ctc2024.demo.stream.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.params.XReadParams;
import redis.clients.jedis.resps.StreamEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsumerService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    public StreamEntry consume(String stream) {
        return consume(stream, "0-0");
    }

    public StreamEntry consume(String stream, String entryId) {
        logger.info("Consuming\n\tstream: {}\n\tentryId: {}", stream, entryId);
        Map<String, StreamEntryID> map = new HashMap<>();
        map.put(stream, new StreamEntryID(entryId.getBytes()));
        List<Map.Entry<String, List<StreamEntry>>> result = jedis.xread(XReadParams.xReadParams().count(1).block(10_000), map);
        if (result != null) {
            StreamEntry streamEntry = result.getFirst().getValue().getFirst();
            logger.info("Consumed\n\tstream: {}\n\tentryId: {}\n\tstreamEntry: {}", stream, entryId, streamEntry);
            return streamEntry;
        } else {
            return null;
        }
    }

}
