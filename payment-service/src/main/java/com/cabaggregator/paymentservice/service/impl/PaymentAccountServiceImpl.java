package com.cabaggregator.paymentservice.service.impl;

import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.enums.sort.PaymentAccountSortField;
import com.cabaggregator.paymentservice.core.mapper.PageMapper;
import com.cabaggregator.paymentservice.core.mapper.PaymentAccountMapper;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.exception.ResourceNotFoundException;
import com.cabaggregator.paymentservice.repository.PaymentAccountRepository;
import com.cabaggregator.paymentservice.service.PaymentAccountService;
import com.cabaggregator.paymentservice.service.StripeService;
import com.cabaggregator.paymentservice.util.PageRequestBuilder;
import com.cabaggregator.paymentservice.validator.PaymentAccountValidator;
import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentAccountServiceImpl implements PaymentAccountService {
    private final StripeService stripeService;

    private final PaymentAccountRepository paymentAccountRepository;

    private final PaymentAccountMapper paymentAccountMapper;

    private final PageMapper pageMapper;

    private final PaymentAccountValidator paymentAccountValidator;

    @Override
    public PageDto<PaymentAccountDto> getPageOfPaymentAccounts(
            Integer offset, Integer limit, PaymentAccountSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                paymentAccountMapper.entityPageToDtoPage(
                        paymentAccountRepository.findAll(pageRequest)));
    }

    @Override
    public PaymentAccountDto getPaymentAccount(UUID id) {
        return paymentAccountMapper.entityToDto(
                getPaymentAccountEntity(id));
    }

    @Override
    @Transactional
    public PaymentAccountDto createPaymentAccount(PaymentAccountAddingDto addingDto) {
        paymentAccountValidator.validateIdUniqueness(addingDto.id());

        Customer createdStripeCustomer = stripeService.createCustomer(
                addingDto.email(), addingDto.phoneNumber(), addingDto.firstName(), addingDto.lastName());

        PaymentAccount paymentAccount = paymentAccountMapper.dtoToEntity(addingDto);
        paymentAccount.setStripeCustomerId(createdStripeCustomer.getId());

        return paymentAccountMapper.entityToDto(
                paymentAccountRepository.save(paymentAccount));
    }

    @Override
    @Transactional
    public PaymentAccountDto updatePaymentAccount(UUID id, String stripeCustomerId) {
        PaymentAccount paymentAccount = getPaymentAccountEntity(id);
        paymentAccountValidator.validateStripeCustomerIdUniqueness(stripeCustomerId);

        paymentAccount.setStripeCustomerId(stripeCustomerId);

        return paymentAccountMapper.entityToDto(
                paymentAccountRepository.save(paymentAccount));
    }

    private PaymentAccount getPaymentAccountEntity(UUID id) {
        return paymentAccountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.PAYMENT_WITH_ID_NOT_FOUND,
                        id));
    }
}
