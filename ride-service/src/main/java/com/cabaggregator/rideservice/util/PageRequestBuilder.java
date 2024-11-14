package com.cabaggregator.rideservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageRequestBuilder {
    public static PageRequest buildPageRequest(Integer offset, Integer limit, Sort sort) {
        return PageRequest.of(
                offset - 1,
                limit,
                sort);
    }
}
