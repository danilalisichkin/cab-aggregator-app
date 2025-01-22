package com.cabaggregator.paymentservice.kafka.producer;

import com.cabaggregator.paymentservice.kafka.config.KafkaTopicConfig;
import com.cabaggregator.paymentservice.kafka.dto.PaymentStatusChangingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStatusProducer {
    private final KafkaTopicConfig topicConfig;

    private final KafkaTemplate<String, PaymentStatusChangingDto> kafkaTemplate;

    public void sendMessage(PaymentStatusChangingDto paymentStatus) {
        Message<PaymentStatusChangingDto> message = MessageBuilder
                .withPayload(paymentStatus)
                .setHeader(
                        KafkaHeaders.TOPIC,
                        topicConfig.getPaymentStatus()
                                .getName())
                .build();

        kafkaTemplate.send(message);
    }
}
