package com.cabaggregator.driverservice.kafka.consumer;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.service.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverConsumer {
    private final DriverService driverService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topics.driver.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> message) {
        log.info("Consume kafka Json message -> {}", message.toString());

        DriverAddingDto driver = objectMapper.convertValue(message, DriverAddingDto.class);

        driverService.saveDriver(driver);
    }
}
