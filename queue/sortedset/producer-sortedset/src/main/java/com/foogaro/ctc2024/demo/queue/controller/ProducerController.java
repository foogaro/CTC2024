package com.foogaro.ctc2024.demo.queue.controller;

import com.foogaro.ctc2024.demo.queue.model.MessageDTO;
import com.foogaro.ctc2024.demo.queue.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class ProducerController {

    @Autowired
    private ProducerService service;

    @GetMapping(value = "/sortedset/{key}/{score}/{message}")
    public ResponseEntity produceMessage(@PathVariable("key") String key,
                                         @PathVariable("score") double score,
                                         @PathVariable("message") String message) {
        service.produce(key, score, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sortedset/{key}/{score}")
    public ResponseEntity publishJSON(@PathVariable("key") String key,
                                      @PathVariable("score") double score,
                                      @RequestBody MessageDTO message) {
        service.produce(key, score, message);
        return ResponseEntity.ok().build();
    }

}
