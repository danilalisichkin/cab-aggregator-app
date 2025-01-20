package com.cabaggregator.payoutservice.kafka.util;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.kafka.dto.BalanceOperationRequest;
import org.springframework.stereotype.Component;

@Component
public class BalanceOperationBuilder {
    public BalanceOperationAddingDto buildAddingDtoFromRequest(BalanceOperationRequest operationRequest) {
        return new BalanceOperationAddingDto(
                operationRequest.amount(),
                operationRequest.transcript());
    }
}
