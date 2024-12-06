package com.cabaggregator.payoutservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BalanceOperationSortField {
    ID("id"),
    CREATED_AT("createdAt"),
    TYPE("type"),
    AMOUNT("amount");

    private final String value;
}
