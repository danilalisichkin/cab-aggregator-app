package com.cabaggregator.driverservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum CarSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    LICENSE_PLATE_ASC(Sort.by(Sort.Direction.ASC, "licensePlate")),
    LICENSE_PLATE_DESC(Sort.by(Sort.Direction.DESC, "licensePlate")),
    MAKE_ASC(Sort.by(Sort.Direction.ASC, "make")),
    MAKE_DESC(Sort.by(Sort.Direction.DESC, "make")),
    MODEL_ASC(Sort.by(Sort.Direction.ASC, "model")),
    MODEL_DESC(Sort.by(Sort.Direction.DESC, "model")),
    COLOR_ASC(Sort.by(Sort.Direction.ASC, "color")),
    COLOR_DESC(Sort.by(Sort.Direction.DESC, "color"));

    private final Sort sortValue;
}
