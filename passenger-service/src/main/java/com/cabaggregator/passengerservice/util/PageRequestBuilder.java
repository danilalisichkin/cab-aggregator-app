package com.cabaggregator.passengerservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageRequestBuilder {
    public static PageRequest buildPageRequest(Integer offset, Integer limit, Sort sort) {
        return PageRequest.of(offset, limit, sort);
    }
}
