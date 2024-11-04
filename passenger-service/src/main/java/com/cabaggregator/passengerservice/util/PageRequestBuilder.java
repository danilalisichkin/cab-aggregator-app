package com.cabaggregator.passengerservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageRequestBuilder {
    public PageRequest buildPageRequest(int pageNumber, int pageSize, String sortField, String sortOrder) {
        return PageRequest.of(
                pageNumber - 1,
                pageSize,
                Sort.by(
                        (sortOrder.equalsIgnoreCase("asc")
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC),
                        sortField));
    }
}
