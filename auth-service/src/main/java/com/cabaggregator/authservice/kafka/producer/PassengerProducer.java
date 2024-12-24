package com.cabaggregator.authservice.kafka.producer;

import com.cabaggregator.authservice.kafka.config.KafkaTopicConfig;
import com.cabaggregator.authservice.kafka.dto.PassengerAddingDto;
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
public class PassengerProducer {
    private final KafkaTopicConfig topicConfig;

    private final KafkaTemplate<String, PassengerAddingDto> kafkaTemplate;

    public void sendMessage(PassengerAddingDto passenger) {

        log.info("Json message send -> {}", passenger.toString());
        Message<PassengerAddingDto> message = MessageBuilder
                .withPayload(passenger)
                .setHeader(
                        KafkaHeaders.TOPIC,
                        topicConfig.getPassenger()
                                .getName())
                .build();
        kafkaTemplate.send(message);
    }
}
