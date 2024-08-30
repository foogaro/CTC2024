package com.foogaro.ctc2024.demo.pubsub;

import com.foogaro.ctc2024.demo.pubsub.service.PubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Publisher implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.data.redis.pubsub.channels}")
    private String[] channels;
    @Autowired
    private PubService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Publisher ready to start");
        Random random = new Random();
        while (true) {
            int channel = random.nextInt(3);
            service.publish(channels[channel], System.currentTimeMillis());
        }
    }
}
