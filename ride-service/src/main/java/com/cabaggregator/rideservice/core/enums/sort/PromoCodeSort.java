package com.cabaggregator.rideservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum PromoCodeSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    ORDERED_ASC(Sort.by(Sort.Direction.ASC, "orderTime")),
    ORDERED_DESC(Sort.by(Sort.Direction.DESC, "orderTime")),
    STARTED_ASC(Sort.by(Sort.Direction.ASC, "startTime")),
    STARTED_DESC(Sort.by(Sort.Direction.DESC, "startTime")),
    ENDED_ASC(Sort.by(Sort.Direction.ASC, "endTime")),
    ENDED_DESC(Sort.by(Sort.Direction.DESC, "endTime")),
    COST_ASC(Sort.by(Sort.Direction.ASC, "price")),
    COST_DESC(Sort.by(Sort.Direction.DESC, "price"));

    private final Sort sortValue;
}

