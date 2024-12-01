package com.cabaggregator.ratingservice.core.dto.driver;

import com.cabaggregator.ratingservice.entity.enums.FeedbackOption;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record DriverRateSettingDto(
        @NotNull
        @Min(1)
        @Max(5)
        Integer rate,

        @NotNull
        Set<FeedbackOption> feedbackOptions
) {
}
