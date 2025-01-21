package com.cabaggregator.paymentservice.kafka.consumer;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.kafka.dto.PaymentDefaultRequest;
import com.cabaggregator.paymentservice.kafka.util.PaymentRequestBuilder;
import com.cabaggregator.paymentservice.service.PaymentAccountService;
import com.cabaggregator.paymentservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentAccountService paymentAccountService;

    private final PaymentService paymentService;

    private final PaymentRequestBuilder paymentRequestBuilder;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topics.payment.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> message) {
        PaymentDefaultRequest defaultRequest = objectMapper.convertValue(message, PaymentDefaultRequest.class);

        PaymentCardDto paymentCard = paymentAccountService.getAccountDefaultPaymentCard(
                defaultRequest.paymentAccountId());

        PaymentRequest paymentRequest = paymentRequestBuilder.buildFromDefaultRequestAndPaymentMethod(
                defaultRequest, paymentCard.paymentMethodId());

        paymentService.createPayment(paymentRequest);
    }
}
