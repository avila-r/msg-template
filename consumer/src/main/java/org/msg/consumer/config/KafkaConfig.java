package org.msg.consumer.config;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@RequiredArgsConstructor
@Configuration class KafkaConfig {

    private final KafkaProperties properties;

    @Bean Map<String, Object> configuration() {
        return Map.of (
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
        );
    }

    @Bean ConsumerFactory<String, String> factory(Map<String, Object> configuration) {
        return new DefaultKafkaConsumerFactory<>(configuration);
    }

    @Bean ConcurrentKafkaListenerContainerFactory<String, String> container(ConsumerFactory<String, String> factory) {
        ConcurrentKafkaListenerContainerFactory<String, String> consumer =
                new ConcurrentKafkaListenerContainerFactory<>();

        consumer.setConsumerFactory(factory);
        return consumer;
    }

}
