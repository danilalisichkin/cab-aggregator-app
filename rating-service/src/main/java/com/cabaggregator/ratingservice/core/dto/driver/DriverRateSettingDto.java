package com.cabaggregator.ratingservice.core.dto.driver;

import com.cabaggregator.ratingservice.entity.enums.FeedbackOption;

import java.util.Set;

public record DriverRateSettingDto(
        Integer rate,
        Set<FeedbackOption> feedbackOptions
) {
}
