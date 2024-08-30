package com.foogaro.ctc2024.demo.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.params.XReadGroupParams;
import redis.clients.jedis.resps.StreamEntry;
import redis.clients.jedis.resps.StreamPendingSummary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class ConsumerGroup implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, String> envs = System.getenv();
    @Value("${spring.data.redis.stream.streams}")
    private String[] streams;
//    @Autowired
//    private ConsumerGroupService service;
    @Autowired
    private Jedis jedis;

    private String streamName = envs.get("STREAM_NAME");
    private String groupName = envs.get("GROUP_NAME");
    private String consumerName = envs.get("CONSUMER_NAME");
    private int count = 1;
    private int block = 0;
    private StreamEntryID streamID = StreamEntryID.LAST_ENTRY;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("{} ready to start", getClass().getName());
        createGroup();
        while (true) {
            List<Map.Entry<String, List<StreamEntry>>> consumed = consumeByGroup(StreamEntryID.UNRECEIVED_ENTRY);
            if (consumed != null) ackByGroup(consumed.getFirst().getValue());
            else break;
        }
        consumePendingList();
    }

    private void createGroup() {
        logger.info("Creating group:");
        if (streamName == null) throw new IllegalArgumentException("Stream name is mandatory.");
        if (groupName == null) throw new IllegalArgumentException("Group name is mandatory.");
        if (consumerName == null) throw new IllegalArgumentException("Consumer name is mandatory.");

        try {
            String xgroupCreate = jedis.xgroupCreate(streamName, groupName, streamID, true);
            logger.info("\n\txgroupCreate: {}", xgroupCreate);
        } catch (JedisDataException jde) {
            logger.error(jde.getMessage());
        }
        boolean created = jedis.xgroupCreateConsumer(streamName, groupName, consumerName);
        logger.info("Creating consumer:\n\tStream name: {}\n\tGroup name: {}\n\tConsumer name: {}\n\tCreate: {}", streamName, groupName, consumerName, created);
    }

    private List<Map.Entry<String, List<StreamEntry>>> consumeByGroup(StreamEntryID streamEntryID) {
        logger.info("Consuming...");
        Map<String, StreamEntryID> map = new HashMap<>();
        map.put(streamName, streamEntryID);
        List<Map.Entry<String, List<StreamEntry>>> result = jedis.xreadGroup(groupName, consumerName, XReadGroupParams.xReadGroupParams().count(count), map);
        logger.info("Results: {}", result);
        return result;
    }

    private void ackByGroup(List<StreamEntry> IDs) {
        logger.info("Acking...");
        if (IDs == null || IDs.isEmpty()) return;
        if (new Random().nextInt(100) < 90) {
            Long acked = jedis.xack(streamName, groupName, IDs.getFirst().getID());
            logger.info("Acked IDs: {}", acked);
        }
    }

    private void consumePendingList() {
        logger.info("Consuming Pending List...");

        while (true) {
            StreamPendingSummary summary = jedis.xpending(streamName, groupName);
            if (summary == null) break;

            List<Map.Entry<String, List<StreamEntry>>> consumed = consumeByGroup(summary.getMinId());
            if (consumed != null &&
                    !consumed.isEmpty() &&
                    consumed.getFirst() != null &&
                    consumed.getFirst().getValue() != null &&
                    !consumed.getFirst().getValue().isEmpty()) ackByGroup(consumed.getFirst().getValue());
            else break;
        }
    }


}
