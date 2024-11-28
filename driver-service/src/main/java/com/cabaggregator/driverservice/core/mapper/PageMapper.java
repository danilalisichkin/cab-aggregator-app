package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.page.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public <T> PageDto<T> pageToPageDto(Page<T> page) {
        return new PageDto<>(
                page.getPageable().getPageNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
