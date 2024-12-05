package com.cabaggregator.payoutservice.util;

import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.enums.OperationType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BalanceOperationTestUtil {
    public static final Long ID = 1L;
    public static final Long AMOUNT = 2000L;
    public static final OperationType TYPE = OperationType.DEPOSIT;
    public static final String TRANSCRIPT = "Deposit transaction";
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2024-12-01T12:00:00");

    public static final LocalDateTime TIME_BEFORE_CREATION = CREATED_AT.minusDays(5);
    public static final LocalDateTime TIME_AFTER_CREATION = CREATED_AT.plusDays(5);

    public static final LocalDateTime TIME_INTERVAL_BEFORE_CREATION_START = CREATED_AT.minusDays(5);
    public static final LocalDateTime TIME_INTERVAL_BEFORE_CREATION_END = CREATED_AT.minusDays(1);

    public static BalanceOperation.BalanceOperationBuilder getBalanceOperationBuilder() {
        return BalanceOperation.builder()
                .id(ID)
                .payoutAccount(null)
                .amount(AMOUNT)
                .type(TYPE)
                .transcript(TRANSCRIPT)
                .createdAt(CREATED_AT);
    }
}