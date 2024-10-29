package com.cabaggregator.driverservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Entry to add new driver")
public class DriverAddingDTO {
    @NotNull
    @Pattern(regexp = "^375(15|29|33|44)\\d{7}$",
            message = "{error.invalid.phone.format}")
    private String phoneNumber;

    @NotNull
    @Email
    @Size(max = 50, message = "{error.invalid.length}")
    private String email;

    @NotNull
    @Size(max = 50, message = "{error.invalid.length}")
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal rating;

    private long carId;
}
