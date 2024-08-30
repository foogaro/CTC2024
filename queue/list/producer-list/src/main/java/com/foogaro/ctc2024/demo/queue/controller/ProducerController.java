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

    @GetMapping(value = "/list/{key}/{message}")
    public ResponseEntity produceMessage(@PathVariable("key") String key,
                                 @PathVariable("message") String message) {
        service.produce(key, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list/{key}")
    public ResponseEntity publishJSON(@PathVariable("key") String key,
                                 @RequestBody MessageDTO message) {
        service.produce(key, message);
        return ResponseEntity.ok().build();
    }

}
