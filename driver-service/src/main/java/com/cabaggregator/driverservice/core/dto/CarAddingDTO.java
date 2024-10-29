package com.cabaggregator.driverservice.core.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarAddingDTO {
    @NotNull
    @Pattern(regexp = "^\\d{4} (А|В|Е|І|К|М|Н|О|Р|С|Т|Х){2}-\\d$",
            message = "{error.invalid.licence.plate.format}")
    private String licensePlate;

    @NotNull
    @Size(max = 30, message = "{error.invalid.length}")
    private String make;

    @NotNull
    @Size(max = 50, message = "{error.invalid.length}")
    private String model;

    @NotNull
    @Size(max = 20, message = "{error.invalid.length}")
    private String color;
}
