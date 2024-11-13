package com.cabaggregator.driverservice.core.dto.page;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto {
    @Positive
    private int pageNumber = 1;

    @Positive
    private int pageSize = 10;

    private String sortField = "id";

    @Pattern(regexp = ValidationRegex.SORT_ORDER,
            message = ValidationErrors.INVALID_SORT_ORDER)
    private String sortOrder = "desc";
}
