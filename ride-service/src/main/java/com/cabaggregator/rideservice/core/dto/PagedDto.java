package com.cabaggregator.rideservice.core.dto;

import java.util.List;

public record PagedDto<T>(
        int page,
        int pageSize,
        int totalPages,
        long totalItems,
        int itemsOnPage,
        List<T> content
) {
}
