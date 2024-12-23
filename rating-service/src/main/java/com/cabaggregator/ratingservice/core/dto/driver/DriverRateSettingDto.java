package com.cabaggregator.ratingservice.core.dto.driver;

import com.cabaggregator.ratingservice.entity.enums.FeedbackOption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Schema(description = "Entry to set driver rate")
public record DriverRateSettingDto(
        @NotNull
        @Min(1)
        @Max(5)
        Integer rate,

        @NotNull
        Set<FeedbackOption> feedbackOptions
) {
}
