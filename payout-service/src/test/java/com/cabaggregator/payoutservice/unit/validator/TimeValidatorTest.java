package com.cabaggregator.payoutservice.unit.validator;

import com.cabaggregator.payoutservice.core.constant.ApplicationMessages;
import com.cabaggregator.payoutservice.exception.BadRequestException;
import com.cabaggregator.payoutservice.exception.ValidationErrorException;
import com.cabaggregator.payoutservice.validator.TimeValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
class TimeValidatorTest {
    private final TimeValidator timeValidator = new TimeValidator();

    @Test
    void validateTime_ShouldNotThrowException_WhenTimeIsBeforeOrEqualToNow() {
        LocalDateTime time = LocalDateTime.now().minusDays(1);

        assertThatCode(
                () -> timeValidator.validateTime(time))
                .doesNotThrowAnyException();
    }

    @Test
    void validateTime_ShouldThrowValidationErrorException_WhenTimeIsAfterNow() {
        LocalDateTime time = LocalDateTime.now().plusDays(1);

        assertThatThrownBy(
                () -> timeValidator.validateTime(time))
                .isInstanceOf(ValidationErrorException.class)
                .hasMessage(ApplicationMessages.TIME_AFTER_PRESENT);
    }

    @Test
    void validateTimeRange_ShouldNotThrowException_WhenEndTimeIsAfterStartTime() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        assertThatCode(
                () -> timeValidator.validateTimeRange(startTime, endTime))
                .doesNotThrowAnyException();
    }

    @Test
    void validateTimeRange_ShouldThrowBadRequestException_WhenEndTimeIsBeforeStartTime() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().minusDays(1);

        assertThatThrownBy(
                () -> timeValidator.validateTimeRange(startTime, endTime))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ApplicationMessages.INVALID_TIME_RANGE);
    }

    @Test
    void validateTimeRange_ShouldThrowBadRequestException_WhenStartTimeAndEndTimeAreEqual() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime;

        assertThatThrownBy(
                () -> timeValidator.validateTimeRange(startTime, endTime))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ApplicationMessages.INVALID_TIME_RANGE);
    }
}

