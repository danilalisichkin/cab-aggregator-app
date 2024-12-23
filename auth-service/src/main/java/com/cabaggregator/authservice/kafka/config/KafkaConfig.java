package com.cabaggregator.authservice.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaTopicConfig.class)
public class KafkaConfig {
    private final KafkaTopicConfig kafkaTopicConfig;

    @Bean
    public NewTopic passengerTopic() {
        var passengerTopic = kafkaTopicConfig.getPassenger();
        return new NewTopic(passengerTopic.getName(), passengerTopic.getPartitions(), passengerTopic.getReplicas());
    }

    @Bean
    public NewTopic driverTopic() {
        var driverTopic = kafkaTopicConfig.getDriver();
        return new NewTopic(driverTopic.getName(), driverTopic.getPartitions(), driverTopic.getReplicas());
    }
}
