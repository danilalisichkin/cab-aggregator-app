package com.cabaggregator.payoutservice.util;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BalanceOperationTestUtil {
    public static final Long ID = 1L;
    public static final Long DEPOSIT_AMOUNT = 2000L;
    public static final Long WITHDRAW_AMOUNT = -1000L;
    public static final OperationType TYPE = OperationType.DEPOSIT;
    public static final String TRANSCRIPT = "Deposit transaction";
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2024-12-01T12:00:00");
    public static final Long BALANCE = 1000L;

    public static final LocalDateTime TIME_BEFORE_CREATION = CREATED_AT.minusDays(5);
    public static final LocalDateTime TIME_AFTER_CREATION = CREATED_AT.plusDays(5);

    public static final LocalDateTime TIME_INTERVAL_BEFORE_CREATION_START = CREATED_AT.minusDays(5);
    public static final LocalDateTime TIME_INTERVAL_BEFORE_CREATION_END = CREATED_AT.minusDays(1);

    public static BalanceOperation.BalanceOperationBuilder getBalanceOperationBuilder() {
        return BalanceOperation.builder()
                .id(ID)
                .payoutAccount(null)
                .amount(DEPOSIT_AMOUNT)
                .type(TYPE)
                .transcript(TRANSCRIPT)
                .createdAt(CREATED_AT);
    }

    public static BalanceOperationDto buildBalanceOperationDto() {
        return new BalanceOperationDto(
                ID,
                PayoutAccountTestUtil.ID,
                DEPOSIT_AMOUNT,
                TYPE,
                TRANSCRIPT,
                CREATED_AT);
    }

    public static BalanceOperationAddingDto buildBalanceOperationAddingDto() {
        return new BalanceOperationAddingDto(
                DEPOSIT_AMOUNT,
                TRANSCRIPT);
    }
}