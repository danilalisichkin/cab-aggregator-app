package com.cabaggregator.promocodeservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageRequestBuilder {
    public static PageRequest buildPageRequest(
            Integer offset, Integer limit, String sortField, Sort.Direction sortOrder) {

        return PageRequest.of(offset - 1, limit, Sort.by(sortOrder, sortField));
    }
}
