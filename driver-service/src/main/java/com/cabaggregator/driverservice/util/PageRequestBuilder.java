package com.cabaggregator.driverservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageRequestBuilder {
    public static PageRequest buildPageRequest(Integer offset, Integer limit, Sort sort) {
        return PageRequest.of(
                offset - 1,
                limit,
                sort);
    }
}
