package com.cabaggregator.ratingservice.core.dto.page;

import java.util.List;

public record PageDto<T>(
        Integer page,
        Integer pageSize,
        Integer totalPages,
        List<T> content
) {
}
