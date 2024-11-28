package com.cabaggregator.driverservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CarSortField {
    ID("id"),
    LICENSE_PLATE("licensePlate"),
    MAKE("make"),
    MODEL("model"),
    COLOR("color");

    private final String value;
}
