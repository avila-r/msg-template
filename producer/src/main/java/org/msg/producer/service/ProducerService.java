package org.msg.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service public class ProducerService {

    private final KafkaTemplate<String, String> template;

    public void send(String message) {
        template.send("messages", message)
                .whenComplete(((result, err) -> {
                    if (err != null) {
                        log.info("unable to send message: {}", err.getMessage());
                        return;
                    }

                    log.info (
                            "sent message [{}] with offset [{}] to topic [{}] and partition [{}]",
                            result.getProducerRecord().value(),
                            result.getRecordMetadata().offset(),
                            result.getProducerRecord().topic(),
                            result.getProducerRecord().topic()
                    );
                }
        ));
    }

}
