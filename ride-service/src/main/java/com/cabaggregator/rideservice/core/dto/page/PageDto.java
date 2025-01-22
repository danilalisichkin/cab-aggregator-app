package com.cabaggregator.rideservice.core.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Server response representing a paginated list of items")
public record PageDto<T>(
        Integer page,
        Integer pageSize,
        Integer totalPages,
        List<T> content
) {
}
