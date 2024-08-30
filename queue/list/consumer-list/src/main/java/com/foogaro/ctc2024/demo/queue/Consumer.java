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
    @Value("${spring.data.redis.queue.lists}")
    private String[] lists;
    @Autowired
    private ConsumerService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Consumer ready to start");
        Random random = new Random();
        while (true) {
            int list = random.nextInt(3);
            doTask(lists[list]);
        }
    }

    private void doTask(String key) {
        service.consume(key);
    }

    private void doTaskDLQ(String key) {
        String task = service.consumeDLQ(key, "DLQ");
        DO_VERY_IMPORTANT_STAFF_HERE(task);
        boolean done = service.deleteDLQ("DLQ", 1, task);
        logger.info(done+"");
    }

    private void DO_VERY_IMPORTANT_STAFF_HERE(String task) {
        logger.info("!!!DONE A VERY IMPORTANT TASK HERE!!!\n\t{}", task);
    }


}
