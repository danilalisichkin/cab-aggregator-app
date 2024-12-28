package com.cabaggregator.paymentservice.service;

import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.enums.sort.PaymentAccountSortField;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface PaymentAccountService {
    PageDto<PaymentAccountDto> getPageOfPaymentAccounts(
            Integer offset, Integer limit, PaymentAccountSortField sortBy, Sort.Direction sortOrder);

    PaymentAccountDto getPaymentAccount(UUID id);

    List<PaymentCardDto> getAccountPaymentCards(UUID id);

    PaymentCardDto getAccountDefaultPaymentCard(UUID id);

    void setAccountDefaultPaymentCard(UUID id, String paymentMethodId);

    PaymentAccountDto createPaymentAccount(PaymentAccountAddingDto addingDto);

    PaymentAccountDto updatePaymentAccount(UUID id, String stripeCustomerId);
}
