package com.cabaggregator.authservice.kafka.producer;

import com.cabaggregator.authservice.kafka.config.KafkaTopicConfig;
import com.cabaggregator.authservice.kafka.dto.DriverAddingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverProducer {
    private final KafkaTopicConfig topicConfig;

    private final KafkaTemplate<String, DriverAddingDto> kafkaTemplate;

    public void sendMessage(DriverAddingDto driver) {
        Message<DriverAddingDto> message = MessageBuilder
                .withPayload(driver)
                .setHeader(
                        KafkaHeaders.TOPIC,
                        topicConfig.getDriver()
                                .getName())
                .build();

        log.info("Produce kafka Json message -> {}", message);

        kafkaTemplate.send(message);
    }
}
