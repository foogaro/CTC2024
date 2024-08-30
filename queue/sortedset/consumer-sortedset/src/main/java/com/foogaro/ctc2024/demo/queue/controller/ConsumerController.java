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

    @GetMapping(value = "/sortedset/min/{key}")
    public ResponseEntity consumeMinMessage(@PathVariable("key") String key) {
        return ResponseEntity.ok(service.consumeMin(key));
    }

    @GetMapping(value = "/sortedset/min/{key}/{count}")
    public ResponseEntity consumeMinMessage(@PathVariable("key") String key,
                                 @PathVariable("count") int count) {
        return ResponseEntity.ok(service.consumeMin(key, count));
    }

    @GetMapping(value = "/sortedset/max/{key}")
    public ResponseEntity consumeMaxMessage(@PathVariable("key") String key) {
        return ResponseEntity.ok(service.consumeMax(key));
    }

    @GetMapping(value = "/sortedset/max/{key}/{count}")
    public ResponseEntity consumeMAxMessage(@PathVariable("key") String key,
                                 @PathVariable("count") int count) {
        return ResponseEntity.ok(service.consumeMax(key, count));
    }

}
