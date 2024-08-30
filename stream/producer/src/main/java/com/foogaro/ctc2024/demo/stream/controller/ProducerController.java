package com.foogaro.ctc2024.demo.stream.controller;

import com.foogaro.ctc2024.demo.stream.model.MessageDTO;
import com.foogaro.ctc2024.demo.stream.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream")
public class ProducerController {

    @Autowired
    private ProducerService service;

    @PostMapping("/{key}")
    public ResponseEntity produce(@PathVariable("key") String key,
                                      @RequestBody MessageDTO message) {
        service.produce(key, message);
        return ResponseEntity.ok().build();
    }


}
