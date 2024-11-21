package com.cabaggregator.rideservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum RideSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    VALUE_ASC(Sort.by(Sort.Direction.ASC, "value")),
    VALUE_DESC(Sort.by(Sort.Direction.DESC, "value")),
    DISCOUNT_ASC(Sort.by(Sort.Direction.ASC, "discount")),
    DISCOUNT_DESC(Sort.by(Sort.Direction.DESC, "discount")),
    ORDERED_ASC(Sort.by(Sort.Direction.ASC, "orderTime")),
    ORDERED_DESC(Sort.by(Sort.Direction.DESC, "orderTime")),
    STARTED_ASC(Sort.by(Sort.Direction.ASC, "startTime")),
    STARTED_DESC(Sort.by(Sort.Direction.DESC, "startTime")),
    ENDED_ASC(Sort.by(Sort.Direction.ASC, "endTime")),
    ENDED_DESC(Sort.by(Sort.Direction.DESC, "endTime"));

    private final Sort sortValue;
}