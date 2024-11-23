package com.cabaggregator.rideservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageRequestBuilder {
    public static PageRequest buildPageRequest(
            Integer offset, Integer limit, String sortField, Sort.Direction sortOrder) {

        return PageRequest.of(offset, limit, Sort.by(sortOrder, sortField));
    }
}
