package com.cabaggregator.payoutservice.util;

import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.enums.OperationType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BalanceOperationTestUtil {
    public static final Long ID = 1L;
    public static final Long AMOUNT = 250L;
    public static final Long NEW_BALANCE = PayoutAccountTestUtil.BALANCE + AMOUNT;
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final OperationType TYPE = OperationType.DEPOSIT;

    public static BalanceOperation.BalanceOperationBuilder getBalanceOperationBuilder() {
        return BalanceOperation.builder()
                .id(ID)
                .payoutAccount(null)
                .amount(AMOUNT)
                .newBalance(NEW_BALANCE)
                .type(TYPE)
                .createdAt(CREATED_AT);
    }
}