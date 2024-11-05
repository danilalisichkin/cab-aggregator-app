package com.cabaggregator.driverservice.core.dto.page;

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

