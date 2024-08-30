package com.foogaro.ctc2024.demo.queue.controller;

import com.foogaro.ctc2024.demo.queue.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class ConsumerController {

    @Autowired
    private ConsumerService service;

    @GetMapping(value = "/list/{key}")
    public ResponseEntity consumeMessage(@PathVariable("key") String key) {
        return ResponseEntity.ok(service.consume(key));
    }

    @GetMapping(value = "/list/{key}/{count}")
    public ResponseEntity consumeMessage(@PathVariable("key") String key,
                                 @PathVariable("count") int count) {
        return ResponseEntity.ok(service.consume(key, count));
    }

    @GetMapping(value = "/list/dlq/{srcList}/{destList}")
    public ResponseEntity consumeMessage(@PathVariable("srcList") String srcList,
                                 @PathVariable("destList") String destList) {
        return ResponseEntity.ok(service.consumeDLQ(srcList, destList));
    }

}
