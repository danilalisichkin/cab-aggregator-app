package com.cabaggregator.promocodeservice.util;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationTestUtil {
    public static PageRequest buildPageRequest(int offset, int limit, String sortField, Sort.Direction sortOrder) {
        return PageRequest.of(offset, limit, Sort.by(sortOrder, sortField));
    }

    public static <T> Page<T> buildPageFromSingleElement(T element) {
        return new PageImpl<>(Collections.singletonList(element));
    }

    public static <T> PageDto<T> buildPageDto(Page<T> page) {
        return new PageDto<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent());
    }
}
