package org.msg.producer.config;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@RequiredArgsConstructor
@Configuration class KafkaConfig {

    private final KafkaProperties properties;

    @Bean Map<String, Object> configuration() {
        return Map.of (
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );
    }

    @Bean KafkaAdmin admin(Map<String, Object> configuration) {
        return new KafkaAdmin(configuration);
    }

    @Bean ProducerFactory<String, String> factory(Map<String, Object> configuration) {
        return new DefaultKafkaProducerFactory<>(configuration);
    }

    @Bean KafkaTemplate<String, String> template(ProducerFactory<String, String> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("messages")
                        .partitions(2)
                        .replicas(1)
                        .build()
        );
    }
}
