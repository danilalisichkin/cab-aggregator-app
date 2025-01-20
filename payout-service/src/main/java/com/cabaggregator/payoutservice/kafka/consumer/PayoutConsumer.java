package com.cabaggregator.payoutservice.kafka.consumer;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.kafka.dto.BalanceOperationRequest;
import com.cabaggregator.payoutservice.kafka.util.BalanceOperationBuilder;
import com.cabaggregator.payoutservice.service.PayoutAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutConsumer {
    private final PayoutAccountService payoutAccountService;

    private final BalanceOperationBuilder balanceOperationBuilder;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topics.payout.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> message) {
        BalanceOperationRequest operationRequest = objectMapper.convertValue(message, BalanceOperationRequest.class);

        UUID accountId = operationRequest.accountId();
        BalanceOperationAddingDto addingDto = balanceOperationBuilder.buildAddingDtoFromRequest(operationRequest);

        payoutAccountService.depositToAccount(accountId, addingDto);
    }
}
