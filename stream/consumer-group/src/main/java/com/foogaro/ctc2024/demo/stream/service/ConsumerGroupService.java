package com.foogaro.ctc2024.demo.stream.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.params.XReadGroupParams;
import redis.clients.jedis.resps.StreamEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ConsumerGroupService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Jedis jedis;

    public StreamEntry consume(String stream) {
        return consume(stream, ">", null, null);
    }

/*
     xreadGroup(String groupName, String consumer,
     XReadGroupParams xReadGroupParams,
     Map<String, StreamEntryID> streams)
 */
    public StreamEntry consume(String stream, String groupName, String consumer, Function<Object, Object> udf) {
        logger.info("Consuming\n\tstream: {}\n\tgroupName: {}\n\tconsumer: {}", stream, groupName, consumer);
        Map<String, StreamEntryID> map = new HashMap<>();
        map.put("1", StreamEntryID.UNRECEIVED_ENTRY);
        List<Map.Entry<String, List<StreamEntry>>> result = jedis.xreadGroup(groupName, consumer, new XReadGroupParams().count(1), map);

        //List<Map.Entry<String, List<StreamEntry>>> result = jedis.xread(XReadParams.xReadParams().count(1).block(10_000), map);
        if (result != null) {
            StreamEntry streamEntry = result.getFirst().getValue().getFirst();
            //logger.info("Consumed\n\tstream: {}\n\tentryId: {}\n\tstreamEntry: {}", stream, entryId, streamEntry);
            return streamEntry;
        } else {
            return null;
        }
    }

    private long xack(String stream, String group, StreamEntryID... ids) {
        return jedis.xack(stream, group, ids);
    }



}
