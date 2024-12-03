package com.cabaggregator.promocodeservice.unit.core.mapper;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.mapper.PageMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class PageMapperTest {
    private final PageMapper mapper = new PageMapper();

    @Test
    void pageToPagedDto_ShouldReturnPagedDto_WhenPageIsNotEmpty() {
        final Integer expectedPage = 1;
        final Integer expectedPageSize = 2;
        final Integer expectedTotalPages = 1;
        final int expectedTotalItems = 2;

        List<Object> expectedContent = Arrays.asList("item1", "item2");

        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(pageable.getPageNumber())
                .thenReturn(expectedPage);
        Mockito.when(pageable.getPageSize())
                .thenReturn(expectedPageSize);

        Page<Object> page = new PageImpl<>(expectedContent, pageable, expectedTotalItems);

        PageDto<Object> actual = mapper.pageToPageDto(page);

        assertThat(actual).isNotNull();
        assertThat(actual.page()).isEqualTo(expectedPage);
        assertThat(actual.pageSize()).isEqualTo(expectedPageSize);
        assertThat(actual.totalPages()).isEqualTo(expectedTotalPages);
        assertThat(actual.content()).isEqualTo(expectedContent);
    }

    @Test
    void pageToPagedDto_ShouldReturnPagedDtoWithNoContent_WhenPageIsEmpty() {
        final Integer expectedPage = 1;
        final Integer expectedPageSize = 0;
        final Integer expectedTotalPages = 1;
        final int expectedTotalItems = 0;

        final int requestedTotalItems = 100;

        List<Object> expectedContent = Collections.emptyList();

        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(pageable.getPageNumber())
                .thenReturn(expectedPage);
        Mockito.when(pageable.getPageSize())
                .thenReturn(expectedPageSize);

        Page<Object> page = new PageImpl<>(expectedContent, pageable, requestedTotalItems);

        PageDto<Object> actual = mapper.pageToPageDto(page);

        assertThat(actual).isNotNull();
        assertThat(actual.page()).isEqualTo(expectedPage);
        assertThat(actual.pageSize()).isEqualTo(expectedPageSize);
        assertThat(actual.totalPages()).isEqualTo(expectedTotalPages);
        assertThat(actual.content())
                .isNotNull()
                .hasSize(expectedTotalItems)
                .isEqualTo(expectedContent);
    }
}
