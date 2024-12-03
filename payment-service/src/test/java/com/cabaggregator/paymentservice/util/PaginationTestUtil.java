package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationTestUtil {
    public static PageRequest buildPageRequest(int offset, int limit, String sortField, Sort.Direction sortOrder) {
        return PageRequest.of(offset, limit, Sort.by(sortOrder, sortField));
    }

    public static <T> Page<T> buildPageFromSingleElement(T element) {
        return new PageImpl<>(Collections.singletonList(element));
    }

    public static <T> Page<T> buildPageFromList(List<T> elements) {
        return new PageImpl<>(elements);
    }

    public static <T> Page<T> buildPageFromList(List<T> elements, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(elements,  PageRequest.of(pageNumber, pageSize), elements.size());
    }

    public static <T> PageDto<T> buildPageDto(Page<T> page) {
        return new PageDto<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent());
    }
}
