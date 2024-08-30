package com.foogaro.ctc2024.demo.stream;

import com.foogaro.ctc2024.demo.stream.service.ProducerService;
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
    @Value("${spring.data.redis.stream.streams}")
    private String[] streams;
    @Autowired
    private ProducerService service;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Producer ready to start");
        Random random = new Random();
        int counter = 1;
        while (counter <= 1000) {
            int stream = random.nextInt(1);
            service.produce(streams[stream], counter++);
//            service.produce(streams[stream], System.currentTimeMillis());
//            MessageDTO messageDTO = new MessageDTO(System.currentTimeMillis()+"", System.currentTimeMillis(), streams[stream]);
//            service.produce(streams[stream], messageDTO);
        }
    }

}
