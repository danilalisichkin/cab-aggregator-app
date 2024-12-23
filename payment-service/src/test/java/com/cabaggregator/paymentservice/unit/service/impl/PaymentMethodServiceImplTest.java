package com.cabaggregator.paymentservice.unit.service.impl;

import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.mapper.PaymentMapper;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.exception.BadRequestException;
import com.cabaggregator.paymentservice.exception.ResourceNotFoundException;
import com.cabaggregator.paymentservice.service.StripeService;
import com.cabaggregator.paymentservice.service.impl.PaymentMethodServiceImpl;
import com.cabaggregator.paymentservice.stripe.enums.PaymentMethodTypes;
import com.cabaggregator.paymentservice.util.PaymentAccountTestUtil;
import com.cabaggregator.paymentservice.util.PaymentTestUtil;
import com.cabaggregator.paymentservice.util.StripeTestUtil;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerListPaymentMethodsParams;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceImplTest {

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    @Mock
    private StripeService stripeService;

    @Mock
    private PaymentMapper paymentMapper;

    @Test
    void getPaymentCards_ShouldReturnListOfPaymentCardDto_WhenPaymentCardsExist() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        Customer stripeCustomer = new Customer();
        List<PaymentMethod> paymentCardList =
                Collections.singletonList(StripeTestUtil.buildPaymentMethod());
        List<PaymentCardDto> paymentCardDtoList =
                Collections.singletonList(PaymentTestUtil.buildPaymentCardDto());

        when(stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId()))
                .thenReturn(stripeCustomer);
        when(stripeService.getCustomerPaymentMethods(stripeCustomer, CustomerListPaymentMethodsParams.Type.CARD))
                .thenReturn(paymentCardList);
        when(paymentMapper.methodListToCardDtoList(paymentCardList))
                .thenReturn(paymentCardDtoList);

        List<PaymentCardDto> actual = paymentMethodService.getPaymentCards(paymentAccount);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCardDtoList);

        verify(stripeService).retrieveCustomer(paymentAccount.getStripeCustomerId());
        verify(stripeService).getCustomerPaymentMethods(stripeCustomer, CustomerListPaymentMethodsParams.Type.CARD);
        verify(paymentMapper).methodListToCardDtoList(paymentCardList);
    }

    @Test
    void getDefaultPaymentCard_ShouldReturnPaymentCard_WhenCustomerDefaultPaymentMethodIsCard() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        Customer stripeCustomer = new Customer();
        PaymentMethod paymentMethod = StripeTestUtil.buildPaymentMethod();
        PaymentCardDto paymentCardDto = PaymentTestUtil.buildPaymentCardDto();

        when(stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId()))
                .thenReturn(stripeCustomer);
        when(stripeService.getCustomerDefaultPaymentMethod(stripeCustomer))
                .thenReturn(paymentMethod);
        when(paymentMapper.methodToCardDto(paymentMethod))
                .thenReturn(paymentCardDto);

        PaymentCardDto actual = paymentMethodService.getDefaultPaymentCard(paymentAccount);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(paymentCardDto);

        verify(stripeService).retrieveCustomer(paymentAccount.getStripeCustomerId());
        verify(stripeService).getCustomerDefaultPaymentMethod(stripeCustomer);
        verify(paymentMapper).methodToCardDto(paymentMethod);
    }

    @Test
    void getDefaultPaymentCard_ShouldThrowResourceNotFoundException_WhenCustomerDefaultPaymentNotFound() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        Customer stripeCustomer = new Customer();
        PaymentMethod paymentMethod = StripeTestUtil.buildPaymentMethod();
        paymentMethod.setType(PaymentMethodTypes.PAYPAL);

        when(stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId()))
                .thenReturn(stripeCustomer);
        when(stripeService.getCustomerDefaultPaymentMethod(stripeCustomer))
                .thenReturn(null);

        assertThatThrownBy(
                () -> paymentMethodService.getDefaultPaymentCard(paymentAccount))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(ApplicationMessages.DEFAULT_PAYMENT_METHOD_NOT_FOUND);

        verify(stripeService).retrieveCustomer(paymentAccount.getStripeCustomerId());
        verify(stripeService).getCustomerDefaultPaymentMethod(stripeCustomer);
        verify(paymentMapper, never()).methodToCardDto(paymentMethod);
    }

    @Test
    void getDefaultPaymentCard_ShouldThrowResourceNotFoundException_WhenCustomerDefaultPaymentMethodIsNotCard() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        Customer stripeCustomer = new Customer();
        PaymentMethod paymentMethod = StripeTestUtil.buildPaymentMethod();
        paymentMethod.setType(PaymentMethodTypes.PAYPAL);

        when(stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId()))
                .thenReturn(stripeCustomer);
        when(stripeService.getCustomerDefaultPaymentMethod(stripeCustomer))
                .thenReturn(paymentMethod);

        assertThatThrownBy(
                () -> paymentMethodService.getDefaultPaymentCard(paymentAccount))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(ApplicationMessages.DEFAULT_PAYMENT_METHOD_NOT_CARD);

        verify(stripeService).retrieveCustomer(paymentAccount.getStripeCustomerId());
        verify(stripeService).getCustomerDefaultPaymentMethod(stripeCustomer);
        verify(paymentMapper, never()).methodToCardDto(paymentMethod);
    }


    @Test
    void setDefaultPaymentCard_ShouldSetCustomerDefaultPaymentAsCart_WhenPaymentMethodIsCard() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        String paymentMethodId = StripeTestUtil.METHOD_ID;
        Customer stripeCustomer = new Customer();
        PaymentMethod paymentMethod = StripeTestUtil.buildPaymentMethod();

        when(stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId()))
                .thenReturn(stripeCustomer);
        when(stripeService.getPaymentMethod(paymentMethodId))
                .thenReturn(paymentMethod);
        doNothing().when(stripeService).setCustomerDefaultPaymentMethod(stripeCustomer, paymentMethod);

        assertThatCode(
                () -> paymentMethodService.setDefaultPaymentCard(paymentAccount, paymentMethodId))
                .doesNotThrowAnyException();

        verify(stripeService).retrieveCustomer(paymentAccount.getStripeCustomerId());
        verify(stripeService).getPaymentMethod(paymentMethodId);
        verify(stripeService).setCustomerDefaultPaymentMethod(stripeCustomer, paymentMethod);
    }

    @Test
    void setDefaultPaymentCard_ShouldThrowBadRequestException_WhenCustomerDefaultPaymentMethodIsNotCard() {
        PaymentAccount paymentAccount = PaymentAccountTestUtil.getPaymentAccountBuilder().build();
        String paymentMethodId = StripeTestUtil.METHOD_ID;
        Customer stripeCustomer = new Customer();
        PaymentMethod paymentMethod = StripeTestUtil.buildPaymentMethod();
        paymentMethod.setType(PaymentMethodTypes.PAYPAL);

        when(stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId()))
                .thenReturn(stripeCustomer);
        when(stripeService.getPaymentMethod(paymentMethodId))
                .thenReturn(paymentMethod);

        assertThatThrownBy(
                () -> paymentMethodService.setDefaultPaymentCard(paymentAccount, paymentMethodId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ApplicationMessages.PROVIDED_PAYMENT_METHOD_NOT_CARD);

        verify(stripeService).retrieveCustomer(paymentAccount.getStripeCustomerId());
        verify(stripeService).getPaymentMethod(paymentMethodId);
        verify(stripeService, never()).setCustomerDefaultPaymentMethod(stripeCustomer, paymentMethod);
    }
}
