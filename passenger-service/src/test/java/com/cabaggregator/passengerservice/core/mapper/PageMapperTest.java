package com.cabaggregator.passengerservice.core.mapper;

import com.cabaggregator.passengerservice.core.dto.PagedDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class PageMapperTest {
    private final PageMapper mapper = new PageMapper();

    @Test
    void pageToPagedDto_ShouldReturnPagedDto_WhenPageIsNotNull() {
        final int expectedPage = 1;
        final int expectedPageSize = 2;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final long expectedItemsOnPage = 2;

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
        assertThat(result.totalItems()).isEqualTo(expectedTotalItems);
        assertThat(result.itemsOnPage()).isEqualTo(expectedItemsOnPage);
        assertThat(result.content()).isEqualTo(expectedContent);
    }

    @Test
    void pageToPagedDto_ShouldReturnNull_WhenPageIsNull() {
        assertThat(mapper.pageToPagedDto(null)).isNull();
    }
}
