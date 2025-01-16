package com.cabaggregator.rideservice.kafka.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.kafka.topics")
public class KafkaTopicConfig {
    private final Topic rate = new Topic();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Topic {
        private String name;
        private int partitions;
        private short replicas;
    }
}
