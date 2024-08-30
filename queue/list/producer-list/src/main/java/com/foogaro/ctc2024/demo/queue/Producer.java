package com.foogaro.ctc2024.demo.queue;

import com.foogaro.ctc2024.demo.queue.model.MessageDTO;
import com.foogaro.ctc2024.demo.queue.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Producer implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.data.redis.queue.lists}")
    private String[] lists;
    @Autowired
    private ProducerService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Producer ready to start");
        Random random = new Random();
        while (true) {
            int list = random.nextInt(3);
            //service.produce(lists[list], System.currentTimeMillis());
            MessageDTO messageDTO = new MessageDTO(System.currentTimeMillis()+"", System.currentTimeMillis(), lists[list]);
            service.produce(lists[list], messageDTO);
        }
    }
}
