package com.cabaggregator.payoutservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayoutAccountSortField {
    ID("id"),
    CREATED_AT("createdAt");

    private final String value;
}
