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
    @Value("${spring.data.redis.queue.sortedsets}")
    private String[] sortedsets;
    @Autowired
    private ProducerService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Producer ready to start");
        Random random = new Random();
        while (true) {
            int sortedset = random.nextInt(3);
            double score = random.nextDouble(0,100);
            //service.produce(sortedsets[sortedset], score, System.currentTimeMillis());
            MessageDTO messageDTO = new MessageDTO(System.currentTimeMillis()+"", System.currentTimeMillis(), sortedsets[sortedset]);
            service.produce(sortedsets[sortedset], score, messageDTO);
        }
    }
}
