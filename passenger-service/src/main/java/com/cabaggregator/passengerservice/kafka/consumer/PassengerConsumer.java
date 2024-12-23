package com.cabaggregator.passengerservice.kafka.consumer;

import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.service.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerConsumer {
    private final PassengerService passengerService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topics.passenger.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> message) {
        log.info("Consume kafka Json message -> {}", message.toString());

        PassengerAddingDto passenger = objectMapper.convertValue(message, PassengerAddingDto.class);

        passengerService.savePassenger(passenger);
    }
}
