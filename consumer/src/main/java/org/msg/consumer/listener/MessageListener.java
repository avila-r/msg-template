package org.msg.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component class MessageListener {

    @KafkaListener(groupId = "group-1", topics = "messages", containerFactory = "container")
    void listen(String message) {
        log.info("received message: {}", message);
    }

}
