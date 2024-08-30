package com.foogaro.ctc2024.demo.stream;

import com.foogaro.ctc2024.demo.stream.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.resps.StreamEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class Consumer implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, String> envs = System.getenv();
    @Value("${spring.data.redis.stream.streams}")
    private String[] streams;
    @Autowired
    private ConsumerService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            logger.info("{} ready to start", getClass().getName());
            doConsumeWithMoreConsumers();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void doConsumeWithMoreConsumers() {
        logger.info("{} ready to start", getClass().getName());
        String nextId = "0-0";
        while (true) {
            Map<String, StreamEntryID> map = new HashMap<>();
            int index = 0;
            //int index = random.nextInt(3);
            String stream = envs.getOrDefault("STREAM_NAME", streams[index]);
            logger.info("stream:\n\t{}", stream);
            StreamEntry streamEntry = service.consume(stream, nextId);
            if (streamEntry != null) {
                nextId = streamEntry.getID().toString();
            } else {
                logger.info("End of stream reached.\n\tStream: {}\n\tID: {}", stream, nextId);
                break;
            }
        }
    }

    private void doConsume() {
        logger.info("{} ready to start", getClass().getName());
        Random random = new Random();
        String[] nextId = {"0-0","0-0","0-0"};
        while (true) {
            Map<String, StreamEntryID> map = new HashMap<>();
            int index = 0;
            //int index = random.nextInt(3);
            String stream = envs.getOrDefault("STREAM_NAME", streams[index]);
            logger.info("stream:\n\t{}", stream);
            StreamEntry streamEntry = service.consume(stream, nextId[index]);
            if (streamEntry != null) {
                nextId[index] = streamEntry.getID().toString();
            } else {
                logger.info("End of stream reached.\n\tStream: {}\n\tID: {}", stream, nextId[index]);
                break;
            }
        }
    }
}
