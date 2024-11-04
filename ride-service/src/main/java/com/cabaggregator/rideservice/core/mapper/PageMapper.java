package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.PagedDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public <T> PagedDto<T> pageToPagedDto(Page<T> page) {
        if (page == null) {
            return null;
        }

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
