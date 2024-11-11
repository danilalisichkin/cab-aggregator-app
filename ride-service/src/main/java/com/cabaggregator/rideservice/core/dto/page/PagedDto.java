package com.cabaggregator.rideservice.core.dto.page;

import java.util.List;

public record PagedDto<T>(
        Integer page,
        Integer pageSize,
        Integer totalPages,
        List<T> content
) {
}
