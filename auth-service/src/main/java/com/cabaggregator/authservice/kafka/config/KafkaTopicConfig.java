package com.cabaggregator.authservice.kafka.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.kafka.topics")
public class KafkaTopicConfig {
    private final Topic passenger = new Topic();
    private final Topic driver = new Topic();

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
