package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.page.PagedDto;
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
                page.getContent()
        );
        return pagedDto;
    }
}
