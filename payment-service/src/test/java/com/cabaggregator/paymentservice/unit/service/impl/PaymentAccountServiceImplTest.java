package com.cabaggregator.paymentservice.unit.service.impl;

import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.enums.sort.PaymentAccountSortField;
import com.cabaggregator.paymentservice.core.mapper.PageMapper;
import com.cabaggregator.paymentservice.core.mapper.PaymentAccountMapper;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.exception.DataUniquenessConflictException;
import com.cabaggregator.paymentservice.exception.ResourceNotFoundException;
import com.cabaggregator.paymentservice.repository.PaymentAccountRepository;
import com.cabaggregator.paymentservice.service.PaymentMethodService;
import com.cabaggregator.paymentservice.service.StripeService;
import com.cabaggregator.paymentservice.service.impl.PaymentAccountServiceImpl;
import com.cabaggregator.paymentservice.util.PageRequestBuilder;
import com.cabaggregator.paymentservice.util.PaginationTestUtil;
import com.cabaggregator.paymentservice.util.PaymentAccountTestUtil;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import com.cabaggregator.paymentservice.util.StripeTestUtil;
import com.cabaggregator.paymentservice.validator.PaymentAccountValidator;
import com.stripe.model.Customer;
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

import java.util.Collections;
import java.util.List;
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
class PaymentAccountServiceImplTest {

    @InjectMocks
    private PaymentAccountServiceImpl paymentAccountService;

    @Mock
    private StripeService stripeService;

    @Mock
    private PaymentMethodService paymentMethodService;

    @Mock
    private PaymentAccountRepository paymentAccountRepository;

    @Mock
    private PaymentAccountMapper paymentAccountMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private PaymentAccountValidator paymentAccountValidator;

    @Test
    void getPageOfPaymentAccounts_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        PaymentAccountSortField sortBy = PaymentAccountSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        PaymentAccountDto accountDto = PaymentAccountTestUtil.buildPaymentAccountDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<PaymentAccount> accountPage = PaginationTestUtil.buildPageFromSingleElement(account);
        Page<PaymentAccountDto> accountDtoPage = PaginationTestUtil.buildPageFromSingleElement(accountDto);
        PageDto<PaymentAccountDto> pageDto = PaginationTestUtil.buildPageDto(accountDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(paymentAccountRepository.findAll(pageRequest))
                    .thenReturn(accountPage);
            when(paymentAccountMapper.entityPageToDtoPage(accountPage))
                    .thenReturn(accountDtoPage);
            when(pageMapper.pageToPageDto(accountDtoPage))
                    .thenReturn(pageDto);

            PageDto<PaymentAccountDto> actual =
                    paymentAccountService.getPageOfPaymentAccounts(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(paymentAccountRepository).findAll(pageRequest);
            verify(paymentAccountMapper).entityPageToDtoPage(accountPage);
            verify(pageMapper).pageToPageDto(accountDtoPage);
        }
    }

    @Test
    void getPaymentAccount_ShouldReturnPaymentAccount_WhenPaymentAccountFound() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        PaymentAccountDto accountDto = PaymentAccountTestUtil.buildPaymentAccountDto();

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(paymentAccountMapper.entityToDto(account))
                .thenReturn(accountDto);

        PaymentAccountDto actual = paymentAccountService.getPaymentAccount(account.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(accountDto);

        verify(paymentAccountRepository).findById(account.getId());
        verify(paymentAccountMapper).entityToDto(account);
    }

    @Test
    void getPaymentAccount_ShouldThrowResourceNotFoundException_WhenPaymentAccountNotFound() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentAccountService.getPaymentAccount(account.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(paymentAccountRepository).findById(account.getId());
        verifyNoInteractions(paymentAccountMapper);
    }

    @Test
    void getAccountPaymentCards_ShouldReturnPaymentCards_WhenPaymentAccountFound() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        List<PaymentCardDto> paymentCards = Collections.singletonList(PaymentTestUtil.buildPaymentCardDto());

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(paymentMethodService.getPaymentCards(account))
                .thenReturn(paymentCards);

        List<PaymentCardDto> actual = paymentAccountService.getAccountPaymentCards(account.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCards);

        verify(paymentAccountRepository).findById(account.getId());
        verify(paymentMethodService).getPaymentCards(account);
    }

    @Test
    void getAccountPaymentCards_ShouldThrowResourceNotFoundException_WhenPaymentAccountNotFound() {
        UUID accountId = PaymentAccountTestUtil.NOT_EXISTING_ID;

        when(paymentAccountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> paymentAccountService.getAccountPaymentCards(accountId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(paymentAccountRepository).findById(accountId);
        verifyNoInteractions(paymentMethodService);
    }

    @Test
    void getAccountDefaultPaymentCard_ShouldReturnPaymentCard_WhenPaymentAccountFound() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        PaymentCardDto paymentCard = PaymentTestUtil.buildPaymentCardDto();

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        when(paymentMethodService.getDefaultPaymentCard(account))
                .thenReturn(paymentCard);

        PaymentCardDto actual = paymentAccountService.getAccountDefaultPaymentCard(account.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCard);

        verify(paymentAccountRepository).findById(account.getId());
        verify(paymentMethodService).getDefaultPaymentCard(account);
    }

    @Test
    void getAccountDefaultPaymentCard_ShouldThrowResourceNotFoundException_WhenPaymentAccountNotFound() {
        UUID accountId = PaymentAccountTestUtil.NOT_EXISTING_ID;

        when(paymentAccountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> paymentAccountService.getAccountDefaultPaymentCard(accountId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(paymentAccountRepository).findById(accountId);
        verifyNoInteractions(paymentMethodService);
    }

    @Test
    void setAccountDefaultPaymentCard_ShouldSetDefaultPaymentCard_WhenPaymentAccountFound() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        String paymentMethodId = StripeTestUtil.METHOD_ID;

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        doNothing().when(paymentMethodService).setDefaultPaymentCard(account, paymentMethodId);

        assertThatCode(
                () -> paymentAccountService.setAccountDefaultPaymentCard(account.getId(), paymentMethodId))
                .doesNotThrowAnyException();

        verify(paymentAccountRepository).findById(account.getId());
        verify(paymentMethodService).setDefaultPaymentCard(account, paymentMethodId);
    }

    @Test
    void setAccountDefaultPaymentCard_ShouldThrowResourceNotFoundException_WhenPaymentAccountNotFound() {
        UUID accountId = PaymentAccountTestUtil.NOT_EXISTING_ID;
        String paymentMethodId = StripeTestUtil.METHOD_ID;

        when(paymentAccountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> paymentAccountService.setAccountDefaultPaymentCard(accountId, paymentMethodId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(paymentAccountRepository).findById(accountId);
        verifyNoInteractions(paymentMethodService);
    }

    @Test
    void createPaymentAccount_ShouldReturnPaymentAccount_WhenPaymentAccountIsValid() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        PaymentAccountDto accountDto = PaymentAccountTestUtil.buildPaymentAccountDto();
        PaymentAccountAddingDto addingDto = PaymentAccountTestUtil.buildPaymentAccountAddingDto();
        Customer customer = new Customer();

        doNothing().when(paymentAccountValidator).validateIdUniqueness(addingDto.id());
        when(stripeService.createCustomer(
                addingDto.email(), addingDto.phoneNumber(), addingDto.firstName(), addingDto.lastName()))
                .thenReturn(customer);
        when(paymentAccountMapper.dtoToEntity(addingDto))
                .thenReturn(account);
        when(paymentAccountRepository.save(account))
                .thenReturn(account);
        when(paymentAccountMapper.entityToDto(account))
                .thenReturn(accountDto);

        PaymentAccountDto actual = paymentAccountService.createPaymentAccount(addingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(accountDto);

        verify(paymentAccountValidator).validateIdUniqueness(addingDto.id());
        verify(stripeService).createCustomer(
                addingDto.email(), addingDto.phoneNumber(), addingDto.firstName(), addingDto.lastName());
        verify(paymentAccountMapper).dtoToEntity(addingDto);
        verify(paymentAccountRepository).save(account);
        verify(paymentAccountMapper).entityToDto(account);
    }

    @Test
    void createPaymentAccount_ShouldThrowDataUniquenessException_WhenPaymentAccountIdNotUnique() {
        PaymentAccountAddingDto addingDto = PaymentAccountTestUtil.buildPaymentAccountAddingDto();

        doThrow(new DataUniquenessConflictException("error"))
                .when(paymentAccountValidator).validateIdUniqueness(addingDto.id());

        assertThatThrownBy(
                () -> paymentAccountService.createPaymentAccount(addingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(paymentAccountValidator).validateIdUniqueness(addingDto.id());
        verifyNoInteractions(paymentAccountMapper, paymentAccountRepository);
    }

    @Test
    void updatePaymentAccount_ShouldReturnPaymentAccount_WhenPaymentAccountFound() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        PaymentAccountDto accountDto = PaymentAccountTestUtil.buildPaymentAccountDto();
        String newStripeCustomerId = PaymentAccountTestUtil.STRIPE_CUSTOMER_ID;

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        doNothing().when(paymentAccountValidator).validateStripeCustomerIdUniqueness(newStripeCustomerId);
        when(paymentAccountRepository.save(account))
                .thenReturn(account);
        when(paymentAccountMapper.entityToDto(account))
                .thenReturn(accountDto);

        PaymentAccountDto actual = paymentAccountService.updatePaymentAccount(account.getId(), newStripeCustomerId);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(accountDto);

        verify(paymentAccountRepository).findById(account.getId());
        verify(paymentAccountValidator).validateStripeCustomerIdUniqueness(newStripeCustomerId);
        verify(paymentAccountRepository).save(account);
        verify(paymentAccountMapper).entityToDto(account);
    }

    @Test
    void updatePaymentAccount_ShouldThrowDataUniquenessException_WhenStripeCustomerIdNotUnique() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        String notUniqueStripeAccountId = PaymentAccountTestUtil.STRIPE_CUSTOMER_ID;

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.of(account));
        doThrow(new DataUniquenessConflictException("error"))
                .when(paymentAccountValidator).validateStripeCustomerIdUniqueness(notUniqueStripeAccountId);

        assertThatThrownBy(
                () -> paymentAccountService.updatePaymentAccount(account.getId(), notUniqueStripeAccountId))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(paymentAccountRepository).findById(account.getId());
        verify(paymentAccountValidator).validateStripeCustomerIdUniqueness(notUniqueStripeAccountId);
        verifyNoMoreInteractions(paymentAccountRepository);
        verifyNoInteractions(paymentAccountMapper);
    }

    @Test
    void updatePaymentAccount_ShouldThrowResourceNotFoundException_WhenPaymentAccountNotFound() {
        PaymentAccount account = PaymentAccountTestUtil.buildDefaultPaymentAccount();
        String newStripeCustomerId = PaymentAccountTestUtil.STRIPE_CUSTOMER_ID;

        when(paymentAccountRepository.findById(account.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> paymentAccountService.updatePaymentAccount(account.getId(), newStripeCustomerId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(paymentAccountRepository).findById(account.getId());
        verifyNoInteractions(paymentAccountValidator);
        verifyNoInteractions(paymentAccountValidator, paymentAccountMapper);
    }
}
