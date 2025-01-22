package com.cabaggregator.rideservice.unit.core.mapper;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.mapper.PageMapper;
import com.cabaggregator.rideservice.util.PaginationTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class PageMapperTest {
    private final PageMapper mapper = new PageMapper();

    @Test
    void pageToPagedDto_ShouldReturnPagedDto_WhenPageIsNotEmpty() {
        Integer pageNumber = 0;
        Integer pageSize = 10;
        List<Object> expectedContent = Arrays.asList("item1", "item2");
        Page<Object> page = PaginationTestUtil.buildPageFromList(expectedContent, pageNumber, pageSize);

        PageDto<Object> actual = mapper.pageToPageDto(page);

        assertThat(actual).isNotNull();
        assertThat(actual.page()).isEqualTo(pageNumber);
        assertThat(actual.pageSize()).isEqualTo(pageSize);
        assertThat(actual.totalPages()).isEqualTo(page.getTotalPages());
        assertThat(actual.content()).isEqualTo(expectedContent);
    }

    @Test
    void pageToPagedDto_ShouldReturnPagedDtoWithNoContent_WhenPageIsEmpty() {
        Integer pageNumber = 0, pageSize = 10;
        List<Object> expectedContent = Collections.emptyList();
        Page<Object> page = PaginationTestUtil.buildPageFromList(expectedContent, pageNumber, pageSize);

        PageDto<Object> actual = mapper.pageToPageDto(page);

        assertThat(actual).isNotNull();
        assertThat(actual.page()).isEqualTo(pageNumber);
        assertThat(actual.pageSize()).isEqualTo(pageSize);
        assertThat(actual.totalPages()).isEqualTo(page.getTotalPages());
        assertThat(actual.content())
                .isNotNull()
                .isEqualTo(expectedContent);
    }
}
