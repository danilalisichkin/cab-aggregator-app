package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public <T> PagedDto<T> pageToPagedDto(Page<T> page) {
        PagedDto<T> pagedDto = new PagedDto<>(
                page.getPageable().getPageNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getPageable().getPageSize(),
                page.getContent()
        );
        return pagedDto;
    }
}
