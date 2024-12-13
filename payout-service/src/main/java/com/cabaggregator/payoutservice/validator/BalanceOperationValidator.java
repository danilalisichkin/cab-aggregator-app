package com.cabaggregator.payoutservice.validator;

import com.cabaggregator.payoutservice.core.constant.ApplicationMessages;
import com.cabaggregator.payoutservice.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceOperationValidator {

    public void validateWithdrawAmount(Long balance, Long operationAmount) {
        if (operationAmount >= 0) {
            throw new BadRequestException(ApplicationMessages.WITHDRAWING_AMOUNT_MUST_BE_NEGATIVE);
        }
        if (balance + operationAmount < 0) {
            throw new BadRequestException(ApplicationMessages.INSUFFICIENT_FUNDS_IN_THE_ACCOUNT);
        }
    }

    public void validateDepositAmount(Long operationAmount) {
        if (operationAmount <= 0) {
            throw new BadRequestException(ApplicationMessages.DEPOSIT_AMOUNT_MUST_BE_POSITIVE);
        }
    }
}
