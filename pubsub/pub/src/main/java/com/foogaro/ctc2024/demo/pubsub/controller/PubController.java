package com.foogaro.ctc2024.demo.pubsub.controller;

import com.foogaro.ctc2024.demo.pubsub.model.MessageDTO;
import com.foogaro.ctc2024.demo.pubsub.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pub")
public class PubController {

    @Autowired
    private PubService service;

    @GetMapping(value = "/{channel}/{message}")
    public ResponseEntity publishMessage(@PathVariable("channel") String channel,
                                 @PathVariable("message") String message) {
        service.publish(channel, message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/json/{channel}/{id}/{name}")
    public ResponseEntity publishJSON(@PathVariable("channel") String channel,
                                 @PathVariable("id") String id,
                                 @PathVariable("name") String name) {
        MessageDTO message = new MessageDTO(id, System.currentTimeMillis(), name);
        service.publish(channel, message);
        return ResponseEntity.ok().build();
    }

}
