package com.cabaggregator.rideservice.kafka.producer;

import com.cabaggregator.rideservice.kafka.config.KafkaTopicConfig;
import com.cabaggregator.rideservice.kafka.dto.RateAddingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateProducer {
    private final KafkaTopicConfig topicConfig;

    private final KafkaTemplate<String, RateAddingDto> kafkaTemplate;

    public void sendMessage(RateAddingDto rate) {
        Message<RateAddingDto> message = MessageBuilder
                .withPayload(rate)
                .setHeader(
                        KafkaHeaders.TOPIC,
                        topicConfig.getRate()
                                .getName())
                .build();

        kafkaTemplate.send(message);
    }
}
