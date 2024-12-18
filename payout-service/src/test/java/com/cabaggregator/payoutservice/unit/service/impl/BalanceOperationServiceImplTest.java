package com.cabaggregator.payoutservice.unit.service.impl;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.core.mapper.BalanceOperationMapper;
import com.cabaggregator.payoutservice.core.mapper.PageMapper;
import com.cabaggregator.payoutservice.entity.BalanceOperation;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.exception.BadRequestException;
import com.cabaggregator.payoutservice.exception.ResourceNotFoundException;
import com.cabaggregator.payoutservice.repository.BalanceOperationRepository;
import com.cabaggregator.payoutservice.service.StripeService;
import com.cabaggregator.payoutservice.service.impl.BalanceOperationServiceImpl;
import com.cabaggregator.payoutservice.util.BalanceOperationTestUtil;
import com.cabaggregator.payoutservice.util.PageRequestBuilder;
import com.cabaggregator.payoutservice.util.PaginationTestUtil;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import com.cabaggregator.payoutservice.util.StripeTestUtil;
import com.cabaggregator.payoutservice.validator.BalanceOperationValidator;
import com.stripe.model.Account;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class BalanceOperationServiceImplTest {

    @InjectMocks
    private BalanceOperationServiceImpl balanceOperationService;

    @Mock
    private StripeService stripeService;

    @Mock
    private BalanceOperationRepository balanceOperationRepository;

    @Mock
    private BalanceOperationValidator balanceOperationValidator;

    @Mock
    private BalanceOperationMapper balanceOperationMapper;

    @Mock
    private PageMapper pageMapper;

    @Test
    void getPageOfBalanceOperations_ShouldReturnPage_WhenValidParams() {
        Integer offset = 0;
        Integer limit = 10;
        BalanceOperationSortField sortBy = BalanceOperationSortField.CREATED_AT;
        Sort.Direction sortOrder = Sort.Direction.ASC;
        OperationType operationType = OperationType.DEPOSIT;
        LocalDateTime startTime = BalanceOperationTestUtil.TIME_BEFORE_CREATION;
        LocalDateTime endTime = BalanceOperationTestUtil.TIME_AFTER_CREATION;

        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        BalanceOperation operation =
                BalanceOperationTestUtil.getBalanceOperationBuilder()
                        .payoutAccount(payoutAccount)
                        .build();
        BalanceOperationDto operationDto = BalanceOperationTestUtil.buildBalanceOperationDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<BalanceOperation> operationPage = PaginationTestUtil.buildPageFromSingleElement(operation);
        Page<BalanceOperationDto> operationDtoPage = PaginationTestUtil.buildPageFromSingleElement(operationDto);
        PageDto<BalanceOperationDto> pageDto = PaginationTestUtil.buildPageDto(operationDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(balanceOperationRepository.findAll(any(Specification.class), eq(pageRequest)))
                    .thenReturn(operationPage);
            when(balanceOperationMapper.entityPageToDtoPage(operationPage))
                    .thenReturn(operationDtoPage);
            when(pageMapper.pageToPageDto(operationDtoPage))
                    .thenReturn(pageDto);

            PageDto<BalanceOperationDto> actual = balanceOperationService.getPageOfBalanceOperations(
                    payoutAccount, offset, limit, sortBy, sortOrder, operationType, startTime, endTime);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(balanceOperationRepository).findAll(any(Specification.class), eq(pageRequest));
            verify(balanceOperationMapper).entityPageToDtoPage(operationPage);
            verify(pageMapper).pageToPageDto(operationDtoPage);
        }
    }

    @Test
    void processDeposit_ShouldProcessDepositSuccessfully_WhenValidAmount() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.DEPOSIT_AMOUNT);
        BalanceOperation operation =
                BalanceOperationTestUtil.getBalanceOperationBuilder()
                        .payoutAccount(payoutAccount)
                        .build();
        BalanceOperationDto operationDto = BalanceOperationTestUtil.buildBalanceOperationDto();
        doNothing().when(balanceOperationValidator).validateDepositAmount(operationAddingDto.amount());
        when(balanceOperationRepository.save(any(BalanceOperation.class))).thenReturn(operation);
        when(balanceOperationMapper.entityToDto(operation)).thenReturn(operationDto);

        BalanceOperationDto actual = balanceOperationService.processDeposit(payoutAccount, operationAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(operationDto);

        verify(balanceOperationValidator).validateDepositAmount(operationAddingDto.amount());
        verify(balanceOperationRepository).save(any(BalanceOperation.class));
        verify(balanceOperationMapper).entityToDto(operation);
    }

    @Test
    void processDeposit_ShouldThrowBadRequestException_WhenInvalidAmount() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.WITHDRAW_AMOUNT);

        doThrow(new BadRequestException("errror"))
                .when(balanceOperationValidator).validateDepositAmount(operationAddingDto.amount());

        assertThatThrownBy(
                () -> balanceOperationService.processDeposit(payoutAccount, operationAddingDto))
                .isInstanceOf(BadRequestException.class);

        verify(balanceOperationValidator).validateDepositAmount(operationAddingDto.amount());
        verifyNoMoreInteractions(balanceOperationValidator);
        verifyNoInteractions(balanceOperationRepository, balanceOperationMapper);
    }

    @Test
    void processWithdraw_ShouldProcessWithdrawSuccessfully_WhenValidAmountAndSufficientFunds() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        Long currentBalance = PayoutAccountTestUtil.COMPUTED_BALANCE;
        BalanceOperation operation =
                BalanceOperationTestUtil.getBalanceOperationBuilder()
                        .payoutAccount(payoutAccount)
                        .build();
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.WITHDRAW_AMOUNT);
        BalanceOperationDto operationDto = BalanceOperationTestUtil.buildBalanceOperationDto();
        Account stripeAccount = StripeTestUtil.buildStripeAccount();

        when(balanceOperationRepository.getBalance(payoutAccount)).thenReturn(currentBalance);
        doNothing().when(balanceOperationValidator).validateWithdrawAmount(
                currentBalance, operationAddingDto.amount());
        when(stripeService.retrieveAccount(payoutAccount.getStripeAccountId())).thenReturn(stripeAccount);
        doNothing().when(stripeService).createPayout(stripeAccount, operationAddingDto.amount());
        when(balanceOperationRepository.save(any(BalanceOperation.class))).thenReturn(operation);
        when(balanceOperationMapper.entityToDto(operation)).thenReturn(operationDto);

        BalanceOperationDto result = balanceOperationService.processWithdraw(payoutAccount, operationAddingDto);

        assertThat(result).isEqualTo(operationDto);

        verify(balanceOperationRepository).getBalance(payoutAccount);
        verify(balanceOperationValidator).validateWithdrawAmount(currentBalance, operationAddingDto.amount());
        verify(stripeService).retrieveAccount(payoutAccount.getStripeAccountId());
        verify(stripeService).createPayout(stripeAccount, operationAddingDto.amount());
        verify(balanceOperationRepository).save(any(BalanceOperation.class));
        verify(balanceOperationMapper).entityToDto(operation);
    }

    @Test
    void processWithdraw_ShouldThrowBadRequestException_WhenInsufficientFunds() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        Long currentBalance = PayoutAccountTestUtil.COMPUTED_BALANCE;
        Long withdrawAmount = 2 * BalanceOperationTestUtil.WITHDRAW_AMOUNT;
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(withdrawAmount);

        when(balanceOperationRepository.getBalance(payoutAccount)).thenReturn(currentBalance);
        doThrow(new BadRequestException("error")).when(balanceOperationValidator)
                .validateWithdrawAmount(currentBalance, operationAddingDto.amount());

        assertThatThrownBy(
                () -> balanceOperationService.processWithdraw(payoutAccount, operationAddingDto))
                .isInstanceOf(BadRequestException.class);

        verify(balanceOperationRepository).getBalance(payoutAccount);
        verify(balanceOperationValidator).validateWithdrawAmount(currentBalance, operationAddingDto.amount());
        verifyNoMoreInteractions(balanceOperationRepository, balanceOperationValidator);
        verifyNoInteractions(balanceOperationMapper);
    }

    @Test
    void processWithdraw_ShouldThrowResourceNotFoundException_WhenStripeAccountNotFound() {
        PayoutAccount payoutAccount = PayoutAccountTestUtil.getPayoutAccountBuilder().build();
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.WITHDRAW_AMOUNT);
        Long currentBalance = PayoutAccountTestUtil.COMPUTED_BALANCE;

        when(balanceOperationRepository.getBalance(payoutAccount)).thenReturn(currentBalance);
        when(stripeService.retrieveAccount(payoutAccount.getStripeAccountId()))
                .thenThrow(new ResourceNotFoundException("error"));

        assertThatThrownBy(
                () -> balanceOperationService.processWithdraw(payoutAccount, operationAddingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(balanceOperationRepository).getBalance(payoutAccount);
        verify(balanceOperationValidator).validateWithdrawAmount(currentBalance, operationAddingDto.amount());
        verifyNoMoreInteractions(balanceOperationRepository, balanceOperationValidator);
        verifyNoInteractions(balanceOperationMapper);
    }
}
