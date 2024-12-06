package com.cabaggregator.pricecalculationservice.exception;

import com.cabaggregator.pricecalculationservice.core.constant.ErrorCauses;

public class DataUniquenessConflictException extends ParameterizedException {
    public DataUniquenessConflictException(String messageKey, Object... messageArgs) {
        super(ErrorCauses.UNIQUENESS_CONFLICT, messageKey, messageArgs);
    }
}
