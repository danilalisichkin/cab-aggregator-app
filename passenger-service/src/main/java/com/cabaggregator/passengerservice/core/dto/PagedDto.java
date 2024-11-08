package com.cabaggregator.passengerservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Server response representing a paginated list of items")
public record PagedDto<T>(
        int page,
        int pageSize,
        int totalPages,
        long totalItems,
        int itemsOnPage,
        List<T> content
) {
}
