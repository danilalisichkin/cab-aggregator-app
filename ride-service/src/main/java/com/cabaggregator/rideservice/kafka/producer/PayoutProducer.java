package com.cabaggregator.rideservice.kafka.producer;

import com.cabaggregator.rideservice.kafka.config.KafkaTopicConfig;
import com.cabaggregator.rideservice.kafka.dto.BalanceOperationRequest;
import com.cabaggregator.rideservice.kafka.dto.PaymentDefaultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayoutProducer {
    private final KafkaTopicConfig topicConfig;

    private final KafkaTemplate<String, PaymentDefaultRequest> kafkaTemplate;

    public void sendMessage(BalanceOperationRequest operation) {
        Message<BalanceOperationRequest> message = MessageBuilder
                .withPayload(operation)
                .setHeader(
                        KafkaHeaders.TOPIC,
                        topicConfig.getPayout()
                                .getName())
                .build();

        kafkaTemplate.send(message);
    }
}

