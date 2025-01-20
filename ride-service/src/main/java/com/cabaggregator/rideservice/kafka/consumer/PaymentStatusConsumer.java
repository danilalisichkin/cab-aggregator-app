package com.cabaggregator.rideservice.kafka.consumer;

import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.kafka.dto.PaymentStatusChangingDto;
import com.cabaggregator.rideservice.kafka.util.PaymentStatusConverter;
import com.cabaggregator.rideservice.service.RidePaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentStatusConsumer {
    private final RidePaymentService ridePaymentService;

    private final PaymentStatusConverter paymentStatusConverter;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topics.payment-status.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> message) {
        PaymentStatusChangingDto statusChangingDto = objectMapper.convertValue(message, PaymentStatusChangingDto.class);

        ObjectId rideId = new ObjectId(statusChangingDto.contextId());
        RidePaymentStatus ridePaymentStatus = paymentStatusConverter.convert(statusChangingDto.paymentStatus());

        ridePaymentService.changeRidePaymentStatus(rideId, ridePaymentStatus);
    }
}
