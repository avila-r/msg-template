package org.msg.producer.controller;

import lombok.RequiredArgsConstructor;
import org.msg.producer.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RequestMapping("/messages")
@RestController public class ProducerController {

    private final ProducerService service;

    @PostMapping
    ResponseEntity<?> post(@RequestBody String message) {
        service.send(message);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
