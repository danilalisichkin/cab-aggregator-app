package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.page.PagedDto;
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
public class PageMapperTest {
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

        PagedDto<Object> result = mapper.pageToPagedDto(page);

        assertThat(result).isNotNull();
        assertThat(result.page()).isEqualTo(expectedPage);
        assertThat(result.pageSize()).isEqualTo(expectedPageSize);
        assertThat(result.totalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.content()).isEqualTo(expectedContent);
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

        PagedDto<Object> result = mapper.pageToPagedDto(page);

        assertThat(result).isNotNull();
        assertThat(result.page()).isEqualTo(expectedPage);
        assertThat(result.pageSize()).isEqualTo(expectedPageSize);
        assertThat(result.totalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.content()).isNotNull();
        assertThat(result.content()).hasSize(expectedTotalItems);
        assertThat(result.content()).isEqualTo(expectedContent);
    }
}
