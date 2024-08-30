package com.foogaro.ctc2024.demo.queue;

import com.foogaro.ctc2024.demo.queue.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Consumer implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.data.redis.queue.sortedsets}")
    private String[] sortedsets;
    @Autowired
    private ConsumerService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Consumer ready to start");
        Random random = new Random();
        while (true) {
            int sortedset = random.nextInt(3);
            service.consumeMax(sortedsets[sortedset]);
        }
    }

}
