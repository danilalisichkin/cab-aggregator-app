package com.cabaggregator.rideservice;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderUpdatingDto;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideTestUtil {
    public static final ObjectId RIDE_ID = new ObjectId();
    public static final String DRIVER_ID = "4665e57c-884a-433d-8fd2-55078f29eab9";
    public static final String PASSENGER_ID = "27ea1354-8777-4580-9d12-714eaac84167";
    public static final String PROMO_CODE = PromoCodeTestUtil.VALUE;
    public static final ServiceCategory SERVICE_CATEGORY = ServiceCategory.ECONOMY;
    public static final RideStatus STATUS = RideStatus.PREPARED;
    public static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    public static final String PICKUP_ADDRESS = "Airport, 1st gate";
    public static final String DESTINATION_ADDRESS = "Global mall";
    public static final BigDecimal PRICE = BigDecimal.valueOf(49.50);
    public static final LocalDateTime ORDER_TIME = LocalDateTime.of(2024, 11, 1, 10, 55);
    public static final LocalDateTime START_TIME = LocalDateTime.of(2024, 11, 1, 11, 0);
    public static final LocalDateTime END_TIME = LocalDateTime.of(2024, 11, 1, 11, 40);
    public static final Duration ESTIMATED_DURATION = Duration.ofMinutes(38);

    public static final String UPDATED_PROMO_CODE = "PROMO";
    public static final ServiceCategory UPDATED_SERVICE_CATEGORY = ServiceCategory.BUSINESS;
    public static final RideStatus UPDATED_STATUS = RideStatus.REQUESTED;
    public static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    public static final String UPDATED_PICKUP_ADDRESS = "Airport, 1st gate";
    public static final String UPDATED_DESTINATION_ADDRESS = "Local mall";
    public static final BigDecimal UPDATED_PRICE = BigDecimal.valueOf(99.99);
    public static final LocalDateTime UPDATED_START_TIME = LocalDateTime.of(2024, 11, 1, 9, 0);
    public static final LocalDateTime UPDATED_END_TIME = LocalDateTime.of(2024, 11, 1, 9, 40);

    public static Ride buildRide() {
        return Ride.builder()
                .id(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .promoCode(PROMO_CODE)
                .serviceCategory(SERVICE_CATEGORY)
                .status(STATUS)
                .paymentMethod(PAYMENT_METHOD)
                .pickupAddress(PICKUP_ADDRESS)
                .destinationAddress(DESTINATION_ADDRESS)
                .price(PRICE)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .build();
    }

    public static RideDto buildRideDto() {
        return new RideDto(
                RIDE_ID,
                PASSENGER_ID,
                DRIVER_ID,
                PROMO_CODE,
                SERVICE_CATEGORY,
                STATUS,
                PAYMENT_METHOD,
                PICKUP_ADDRESS,
                DESTINATION_ADDRESS,
                PRICE.setScale(2, RoundingMode.HALF_UP),
                ORDER_TIME,
                START_TIME,
                END_TIME,
                ESTIMATED_DURATION);
    }

    public static RideOrderAddingDto buildOrderAddingDto() {
        return new RideOrderAddingDto(
                SERVICE_CATEGORY,
                PAYMENT_METHOD,
                PICKUP_ADDRESS,
                DESTINATION_ADDRESS);
    }

    public static RideOrderUpdatingDto buildOrderUpdatingDto() {
        return new RideOrderUpdatingDto(
                UPDATED_PAYMENT_METHOD,
                UPDATED_PICKUP_ADDRESS,
                UPDATED_DESTINATION_ADDRESS);
    }
}

