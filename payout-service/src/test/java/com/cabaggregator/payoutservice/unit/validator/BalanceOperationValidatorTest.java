package com.cabaggregator.payoutservice.unit.validator;

import com.cabaggregator.payoutservice.exception.BadRequestException;
import com.cabaggregator.payoutservice.util.BalanceOperationTestUtil;
import com.cabaggregator.payoutservice.validator.BalanceOperationValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
class BalanceOperationValidatorTest {
    private final BalanceOperationValidator balanceOperationValidator = new BalanceOperationValidator();

    @Test
    void validateWithdrawAmount_ShouldNotThrowException_WhenAmountIsNegativeAndSufficientFunds() {
        Long balance = BalanceOperationTestUtil.BALANCE;
        Long amount = BalanceOperationTestUtil.WITHDRAW_AMOUNT;

        assertThatCode(
                () -> balanceOperationValidator.validateWithdrawAmount(balance, amount))
                .doesNotThrowAnyException();
    }

    @Test
    void validateWithdrawAmount_ShouldThrowBadRequestException_WhenAmountIsNotNegative() {
        Long balance = BalanceOperationTestUtil.BALANCE;
        Long amount = (-1) * BalanceOperationTestUtil.WITHDRAW_AMOUNT;

        assertThatThrownBy(
                () -> balanceOperationValidator.validateWithdrawAmount(balance, amount))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void validateWithdrawAmount_ShouldThrowBadRequestException_WhenAmountIsNegativeButInsufficientFunds() {
        Long balance = 0L;
        Long amount = BalanceOperationTestUtil.WITHDRAW_AMOUNT;

        assertThatThrownBy(
                () -> balanceOperationValidator.validateWithdrawAmount(balance, amount))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void validateDepositAmount_ShouldNotThrowException_WhenAmountIsPositive() {
        Long amount = BalanceOperationTestUtil.DEPOSIT_AMOUNT;

        assertThatCode(
                () -> balanceOperationValidator.validateDepositAmount(amount))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDepositAmount_ShouldThrowBadRequestException_WhenAmountIsNotPositive() {
        Long amount = (-1) * BalanceOperationTestUtil.DEPOSIT_AMOUNT;

        assertThatThrownBy(
                () -> balanceOperationValidator.validateDepositAmount(amount))
                .isInstanceOf(BadRequestException.class);
    }
}
