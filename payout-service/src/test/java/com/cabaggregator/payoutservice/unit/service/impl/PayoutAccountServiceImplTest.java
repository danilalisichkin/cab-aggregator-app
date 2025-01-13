package com.cabaggregator.payoutservice.unit.service.impl;

import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.core.enums.sort.PayoutAccountSortField;
import com.cabaggregator.payoutservice.core.mapper.PageMapper;
import com.cabaggregator.payoutservice.core.mapper.PayoutAccountMapper;
import com.cabaggregator.payoutservice.entity.PayoutAccount;
import com.cabaggregator.payoutservice.exception.DataUniquenessConflictException;
import com.cabaggregator.payoutservice.exception.ResourceNotFoundException;
import com.cabaggregator.payoutservice.repository.PayoutAccountRepository;
import com.cabaggregator.payoutservice.service.BalanceOperationService;
import com.cabaggregator.payoutservice.service.StripeService;
import com.cabaggregator.payoutservice.service.impl.PayoutAccountServiceImpl;
import com.cabaggregator.payoutservice.util.BalanceOperationTestUtil;
import com.cabaggregator.payoutservice.util.PageRequestBuilder;
import com.cabaggregator.payoutservice.util.PaginationTestUtil;
import com.cabaggregator.payoutservice.util.PayoutAccountTestUtil;
import com.cabaggregator.payoutservice.util.StripeTestUtil;
import com.cabaggregator.payoutservice.validator.PayoutAccountValidator;
import com.cabaggregator.payoutservice.validator.TimeValidator;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PayoutAccountServiceImplTest {

    @InjectMocks
    private PayoutAccountServiceImpl payoutAccountService;

    @Mock
    private StripeService stripeService;

    @Mock
    private BalanceOperationService balanceOperationService;

    @Mock
    private PayoutAccountRepository payoutAccountRepository;

    @Mock
    private PayoutAccountValidator payoutAccountValidator;

    @Mock
    private TimeValidator timeValidator;

    @Mock
    private PayoutAccountMapper payoutAccountMapper;

    @Mock
    private PageMapper pageMapper;

    @Test
    void getPageOfPayoutAccounts_ShouldReturnPage_WhenValidParams() {
        Integer offset = 0;
        Integer limit = 10;
        PayoutAccountSortField sortBy = PayoutAccountSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        PayoutAccount account = PayoutAccountTestUtil.buildDefaultPayoutAccount();
        PayoutAccountDto accountDto = PayoutAccountTestUtil.buildPayoutAccountDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<PayoutAccount> accountPage = PaginationTestUtil.buildPageFromSingleElement(account);
        Page<PayoutAccountDto> accountDtoPage = PaginationTestUtil.buildPageFromSingleElement(accountDto);
        PageDto<PayoutAccountDto> pageDto = PaginationTestUtil.buildPageDto(accountDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(payoutAccountRepository.findAll(pageRequest))
                    .thenReturn(accountPage);
            when(payoutAccountMapper.entityPageToDtoPage(accountPage))
                    .thenReturn(accountDtoPage);
            when(pageMapper.pageToPageDto(accountDtoPage))
                    .thenReturn(pageDto);

            PageDto<PayoutAccountDto> actual =
                    payoutAccountService.getPageOfPayoutAccounts(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(payoutAccountRepository).findAll(pageRequest);
            verify(payoutAccountMapper).entityPageToDtoPage(accountPage);
            verify(pageMapper).pageToPageDto(accountDtoPage);
        }
    }

    @Test
    void getPayoutAccount_ShouldReturnPayoutAccount_WhenPayoutAccountFound() {
        PayoutAccount account = PayoutAccountTestUtil.buildDefaultPayoutAccount();
        PayoutAccountDto accountDto = PayoutAccountTestUtil.buildPayoutAccountDto();

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(payoutAccountMapper.entityToDto(account))
                .thenReturn(accountDto);

        PayoutAccountDto actual = payoutAccountService.getPayoutAccount(account.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(accountDto);

        verify(payoutAccountRepository).findById(account.getId());
        verify(payoutAccountMapper).entityToDto(account);
    }

    @Test
    void getPayoutAccount_ShouldThrowResourceNotFoundException_WhenPayoutAccountNotFound() {
        PayoutAccount account = PayoutAccountTestUtil.buildDefaultPayoutAccount();

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> payoutAccountService.getPayoutAccount(account.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(payoutAccountRepository).findById(account.getId());
        verifyNoInteractions(payoutAccountMapper);
    }

    @Test
    void createPayoutAccount_ShouldReturnPayoutAccount_WhenPayoutAccountIsValid() {
        PayoutAccount account = PayoutAccountTestUtil.buildDefaultPayoutAccount();
        PayoutAccountDto accountDto = PayoutAccountTestUtil.buildPayoutAccountDto();
        PayoutAccountAddingDto addingDto = PayoutAccountTestUtil.buildPayoutAccountAddingDto();
        Account stripeAccount = StripeTestUtil.buildStripeAccount();

        doNothing().when(payoutAccountValidator).validateIdUniqueness(addingDto.id());
        doNothing().when(payoutAccountValidator).validateStripeAccountUniqueness(addingDto.stripeAccountId());
        when(stripeService.retrieveAccount(stripeAccount.getId()))
                .thenReturn(stripeAccount);
        when(payoutAccountMapper.dtoToEntity(addingDto))
                .thenReturn(account);
        when(payoutAccountRepository.save(account))
                .thenReturn(account);
        when(payoutAccountMapper.entityToDto(account))
                .thenReturn(accountDto);

        PayoutAccountDto actual = payoutAccountService.createPayoutAccount(addingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(accountDto);

        verify(payoutAccountValidator).validateIdUniqueness(addingDto.id());
        verify(payoutAccountValidator).validateStripeAccountUniqueness(addingDto.stripeAccountId());
        verify(stripeService).retrieveAccount(stripeAccount.getId());
        verify(payoutAccountMapper).dtoToEntity(addingDto);
        verify(payoutAccountRepository).save(account);
        verify(payoutAccountMapper).entityToDto(account);
    }

    @Test
    void createPayoutAccount_ShouldThrowDataUniquenessException_WhenPayoutAccountIdNotUnique() {
        PayoutAccountAddingDto addingDto = PayoutAccountTestUtil.buildPayoutAccountAddingDto();

        doThrow(new DataUniquenessConflictException("error"))
                .when(payoutAccountValidator).validateIdUniqueness(addingDto.id());

        assertThatThrownBy(
                () -> payoutAccountService.createPayoutAccount(addingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(payoutAccountValidator).validateIdUniqueness(addingDto.id());
        verifyNoMoreInteractions(payoutAccountMapper);
        verifyNoInteractions(payoutAccountRepository, payoutAccountMapper);
    }

    @Test
    void createPayoutAccount_ShouldThrowDataUniquenessException_WhenStripeAccountIdNotUnique() {
        PayoutAccountAddingDto addingDto = PayoutAccountTestUtil.buildPayoutAccountAddingDto();
        Account stripeAccount = StripeTestUtil.buildStripeAccount();

        doNothing().when(payoutAccountValidator).validateIdUniqueness(addingDto.id());
        doThrow(new DataUniquenessConflictException("error"))
                .when(payoutAccountValidator).validateStripeAccountUniqueness(stripeAccount.getId());

        assertThatThrownBy(
                () -> payoutAccountService.createPayoutAccount(addingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(payoutAccountValidator).validateIdUniqueness(addingDto.id());
        verify(payoutAccountValidator).validateStripeAccountUniqueness(stripeAccount.getId());
        verifyNoInteractions(payoutAccountRepository, payoutAccountMapper);
    }

    @Test
    void updatePayoutAccount_ShouldUpdateStripeAccountId_WhenPayoutAndStripeAccountsExist() {
        PayoutAccount account = PayoutAccountTestUtil.buildDefaultPayoutAccount();
        Account stripeAccount = StripeTestUtil.buildStripeAccount();
        PayoutAccountDto accountDto = PayoutAccountTestUtil.buildPayoutAccountDto();

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(stripeService.retrieveAccount(stripeAccount.getId()))
                .thenReturn(stripeAccount);
        when(payoutAccountRepository.save(account))
                .thenReturn(account);
        when(payoutAccountMapper.entityToDto(account))
                .thenReturn(accountDto);

        PayoutAccountDto actual = payoutAccountService.updatePayoutAccount(account.getId(), stripeAccount.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(accountDto);


        verify(payoutAccountRepository).findById(account.getId());
        verify(stripeService).retrieveAccount(stripeAccount.getId());
        verify(payoutAccountRepository).save(account);
        verify(payoutAccountMapper).entityToDto(account);
    }

    @Test
    void updatePayoutAccount_ShouldThrowResourceNotFoundException_WhenPayoutAccountDoesNotExist() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();
        String stripeId = StripeTestUtil.STRIPE_ACCOUNT_ID;

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> payoutAccountService.updatePayoutAccount(account.getId(), stripeId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(payoutAccountRepository).findById(account.getId());
        verifyNoMoreInteractions(payoutAccountRepository);
        verifyNoInteractions(stripeService, payoutAccountMapper);
    }

    @Test
    void updatePayoutAccount_ShouldThrowResourceNotFoundException_WhenStripeAccountDoesNotExist() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();
        String stripeId = StripeTestUtil.NOT_EXISTING_STRIPE_ACCOUNT_ID;

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        doThrow(new ResourceNotFoundException("error"))
                .when(stripeService).retrieveAccount(stripeId);

        assertThatThrownBy(
                () -> payoutAccountService.updatePayoutAccount(account.getId(), stripeId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(payoutAccountRepository).findById(account.getId());
        verify(stripeService).retrieveAccount(stripeId);
        verifyNoMoreInteractions(payoutAccountRepository);
        verifyNoInteractions(payoutAccountMapper);
    }

    @Test
    void setPayoutAccountActivity_ShouldChangeActivity_WhenPayoutAccountExistsAndNewActivityDiffers() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();
        boolean newActive = !account.getActive();
        PayoutAccount changedAccount = account.withActive(newActive);

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(payoutAccountRepository.save(changedAccount))
                .thenReturn(changedAccount);

        assertThatCode(
                () -> payoutAccountService.setPayoutAccountActivity(account.getId(), newActive))
                .doesNotThrowAnyException();
        assertThat(account.getActive()).isEqualTo(newActive);

        verify(payoutAccountRepository).findById(account.getId());
        verify(payoutAccountRepository).save(changedAccount);
    }

    @Test
    void setPayoutAccountActivity_ShouldDoNothing_WhenPayoutAccountExistsAndNewActivityDoesNotDiffer() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();
        boolean newActive = account.getActive();

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));

        assertThatCode(
                () -> payoutAccountService.setPayoutAccountActivity(account.getId(), newActive))
                .doesNotThrowAnyException();

        verify(payoutAccountRepository).findById(account.getId());
        verifyNoMoreInteractions(payoutAccountRepository);
    }

    @Test
    void setPayoutAccountActivity_ShouldThrowResourceNotFoundException_WhenPayoutAccountDoesNotExist() {
        UUID accountId = PayoutAccountTestUtil.ID;
        boolean newActive = true;

        when(payoutAccountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> payoutAccountService.setPayoutAccountActivity(accountId, newActive))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(payoutAccountRepository).findById(accountId);
        verifyNoMoreInteractions(payoutAccountRepository);
    }

    @Test
    void getPayoutAccountBalance_ShouldReturnCurrentBalance_WhenPayoutAccountExists() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();
        Long currentBalance = PayoutAccountTestUtil.COMPUTED_BALANCE;

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(balanceOperationService.getAccountBalance(account))
                .thenReturn(currentBalance);

        Long actual = payoutAccountService.getPayoutAccountBalance(account.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(currentBalance);

        verify(payoutAccountRepository).findById(account.getId());
        verify(balanceOperationService).getAccountBalance(account);
    }

    @Test
    void getPayoutAccountBalance_ShouldThrowResourceNotFoundException_WhenPayoutAccountDoesNotExist() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> payoutAccountService.getPayoutAccountBalance(account.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(payoutAccountRepository).findById(account.getId());
        verifyNoInteractions(balanceOperationService);
    }

    @Test
    void getPageOfBalanceOperations_ShouldReturnPageOfBalanceOperations_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        BalanceOperationSortField sortBy = BalanceOperationSortField.CREATED_AT;
        Sort.Direction sortOrder = Sort.Direction.ASC;
        OperationType operationType = OperationType.DEPOSIT;
        LocalDateTime startTime = BalanceOperationTestUtil.TIME_BEFORE_CREATION;
        LocalDateTime endTime = BalanceOperationTestUtil.TIME_AFTER_CREATION;

        PayoutAccount payoutAccount = PayoutAccountTestUtil.buildDefaultPayoutAccount();
        BalanceOperationDto operationDto = BalanceOperationTestUtil.buildBalanceOperationDto();

        Page<BalanceOperationDto> operationDtoPage = PaginationTestUtil.buildPageFromSingleElement(operationDto);
        PageDto<BalanceOperationDto> pageDto = PaginationTestUtil.buildPageDto(operationDtoPage);

        doNothing().when(timeValidator).validateTime(startTime);
        doNothing().when(timeValidator).validateTime(endTime);
        doNothing().when(timeValidator).validateTimeRange(startTime, endTime);
        when(payoutAccountRepository.findById(payoutAccount.getId()))
                .thenReturn(Optional.of(payoutAccount));
        when(balanceOperationService.getPageOfBalanceOperations(
                payoutAccount, offset, limit, sortBy, sortOrder, operationType, startTime, endTime))
                .thenReturn(pageDto);

        PageDto<BalanceOperationDto> actual = payoutAccountService.getPageOfBalanceOperations(
                payoutAccount.getId(), offset, limit, sortBy, sortOrder, operationType, startTime, endTime);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(pageDto);

        verify(timeValidator).validateTime(startTime);
        verify(timeValidator).validateTime(endTime);
        verify(timeValidator).validateTimeRange(startTime, endTime);
        verify(payoutAccountRepository).findById(payoutAccount.getId());
        verify(balanceOperationService).getPageOfBalanceOperations(
                payoutAccount, offset, limit, sortBy, sortOrder, operationType, startTime, endTime);
    }

    @Test
    void depositToAccount_ShouldNotThrowException_WhenPayoutAccountExists() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.DEPOSIT_AMOUNT);
        BalanceOperationDto operationDto = BalanceOperationTestUtil.buildBalanceOperationDto();

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(balanceOperationService.processDeposit(account, operationAddingDto))
                .thenReturn(operationDto);

        BalanceOperationDto actual = payoutAccountService.depositToAccount(account.getId(), operationAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(operationDto);

        verify(payoutAccountRepository).findById(account.getId());
        verify(balanceOperationService).processDeposit(account, operationAddingDto);
    }

    @Test
    void depositToAccount_ShouldThrowResourceNotFoundException_WhenPayoutAccountDoesNotExist() {
        UUID accountId = PayoutAccountTestUtil.NOT_EXISTING_ID;
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.DEPOSIT_AMOUNT);

        when(payoutAccountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> payoutAccountService.depositToAccount(accountId, operationAddingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(payoutAccountRepository).findById(accountId);
        verifyNoInteractions(balanceOperationService);
    }

    @Test
    void withdrawFromAccount_ShouldNotThrowException_WhenPayoutAccountExists() {
        PayoutAccount account = PayoutAccountTestUtil.getNotExistingPayoutAccount();
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.WITHDRAW_AMOUNT);
        BalanceOperationDto operationDto = BalanceOperationTestUtil.buildBalanceOperationDto();

        when(payoutAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(balanceOperationService.processWithdraw(account, operationAddingDto))
                .thenReturn(operationDto);

        BalanceOperationDto actual = payoutAccountService.withdrawFromAccount(account.getId(), operationAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(operationDto);

        verify(payoutAccountRepository).findById(account.getId());
        verify(balanceOperationService).processWithdraw(account, operationAddingDto);
    }

    @Test
    void withdrawFromAccount_ShouldThrowResourceNotFoundException_WhenPayoutAccountDoesNotExist() {
        UUID accountId = PayoutAccountTestUtil.NOT_EXISTING_ID;
        BalanceOperationAddingDto operationAddingDto =
                BalanceOperationTestUtil.buildBalanceOperationAddingDto(BalanceOperationTestUtil.DEPOSIT_AMOUNT);

        when(payoutAccountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> payoutAccountService.withdrawFromAccount(accountId, operationAddingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(payoutAccountRepository).findById(accountId);
        verifyNoInteractions(balanceOperationService);
    }
}
