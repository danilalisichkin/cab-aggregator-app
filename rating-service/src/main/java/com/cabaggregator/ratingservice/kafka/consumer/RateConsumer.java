package com.cabaggregator.ratingservice.kafka.consumer;

import com.cabaggregator.ratingservice.kafka.dto.RateAddingDto;
import com.cabaggregator.ratingservice.kafka.mapper.RateAddingDtoMapper;
import com.cabaggregator.ratingservice.service.DriverRateService;
import com.cabaggregator.ratingservice.service.PassengerRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RateConsumer {
    private final DriverRateService driverRateService;

    private final PassengerRateService passengerRateService;

    private final RateAddingDtoMapper rateAddingDtoMapper;

    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${app.kafka.topics.rate.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> message) {
        RateAddingDto rate = objectMapper.convertValue(message, RateAddingDto.class);

        driverRateService.saveDriverRate(
                rateAddingDtoMapper.toDriverAddingDto(rate));
        passengerRateService.savePassengerRate(
                rateAddingDtoMapper.toPassengerAddingDto(rate));
    }
}
