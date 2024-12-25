package com.cabaggregator.driverservice.unit.validator;

import com.cabaggregator.driverservice.exception.ValidationErrorException;
import com.cabaggregator.driverservice.validator.CarDetailsValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
class CarDetailsValidatorTest {
    private final CarDetailsValidator carDetailsValidator = new CarDetailsValidator();

    @Test
    void validateReleaseDate_ShouldThrowValidationErrorException_WhenReleaseDateIsAfterPresent() {
        LocalDate releaseDateAfterPresent = LocalDate.now().plusYears(1);

        assertThatThrownBy(
                () -> carDetailsValidator.validateReleaseDate(releaseDateAfterPresent))
                .isInstanceOf(ValidationErrorException.class);
    }

    @Test
    void validateReleaseDate_ShouldNotThrowAnyException_WhenReleaseDateIsBeforePresent() {
        LocalDate releaseDateBeforePresent = LocalDate.now().minusYears(1);

        assertThatCode(
                () -> carDetailsValidator.validateReleaseDate(releaseDateBeforePresent))
                .doesNotThrowAnyException();
    }
}
