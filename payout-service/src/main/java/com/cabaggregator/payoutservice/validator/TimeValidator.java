package com.cabaggregator.payoutservice.validator;

import com.cabaggregator.payoutservice.core.constant.ApplicationMessages;
import com.cabaggregator.payoutservice.exception.BadRequestException;
import com.cabaggregator.payoutservice.exception.ValidationErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TimeValidator {

    public void validateTime(LocalDateTime time) {
        if (time != null && time.isAfter(LocalDateTime.now())) {
            throw new ValidationErrorException(ApplicationMessages.TIME_AFTER_PRESENT);
        }
    }

    public void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
            throw new BadRequestException(ApplicationMessages.INVALID_TIME_RANGE);
        }
    }
}
