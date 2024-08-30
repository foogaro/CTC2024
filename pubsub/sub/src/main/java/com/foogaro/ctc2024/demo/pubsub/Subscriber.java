package com.foogaro.ctc2024.demo.pubsub;

import com.foogaro.ctc2024.demo.pubsub.service.SubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Component
public class Subscriber implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, String> envs = System.getenv();
    @Autowired
    private Jedis jedis;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            logger.info("{} ready to start", getClass().getName());
            jedis.psubscribe(new SubService(), envs.getOrDefault("SUBSCRIBER_NAME", "*"));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
