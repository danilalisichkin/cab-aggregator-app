package com.cabaggregator.rideservice;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingUpdatingDto;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideTestUtil {
    public static final ObjectId RIDE_ID = new ObjectId();
    public static final Long DRIVER_ID = 1L;
    public static final Long PASSENGER_ID = 2L;
    public static final ObjectId PROMO_CODE_ID = PromoCodeTestUtil.PROMO_CODE_ID;
    public static final String PROMO_CODE = PromoCodeTestUtil.VALUE;
    public static final ServiceCategory SERVICE_CATEGORY = ServiceCategory.ECONOMY;
    public static final RideStatus STATUS = RideStatus.PREPARED;
    public static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    public static final String PICKUP_ADDRESS = "Airport, 1st gate";
    public static final String DESTINATION_ADDRESS = "Global mall";
    public static final BigDecimal COST = BigDecimal.valueOf(49.50);
    public static final LocalDateTime START_TIME = LocalDateTime.of(2024, 11, 1, 11, 0);
    public static final LocalDateTime END_TIME = LocalDateTime.of(2024, 11, 1, 11, 40);

    public static final Long UPDATED_PASSENGER_ID = 3L;
    public static final String UPDATED_PROMO_CODE = "PROMO";
    public static final ServiceCategory UPDATED_SERVICE_CATEGORY = ServiceCategory.BUSINESS;
    public static final RideStatus UPDATED_STATUS = RideStatus.REQUESTED;
    public static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    public static final String UPDATED_PICKUP_ADDRESS = "Airport, 1st gate";
    public static final String UPDATED_DESTINATION_ADDRESS = "Local mall";
    public static final BigDecimal UPDATED_COST = BigDecimal.valueOf(99.99);
    public static final LocalDateTime UPDATED_START_TIME = LocalDateTime.of(2024, 11, 1, 9, 0);
    public static final LocalDateTime UPDATED_END_TIME = LocalDateTime.of(2024, 11, 1, 9, 40);

    public static Ride buildRide() {
        return Ride.builder()
                .id(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .promoCodeId(PROMO_CODE_ID)
                .serviceCategory(SERVICE_CATEGORY)
                .status(STATUS)
                .paymentMethod(PAYMENT_METHOD)
                .pickupAddress(PICKUP_ADDRESS)
                .destinationAddress(DESTINATION_ADDRESS)
                .cost(COST)
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
                COST.setScale(2, RoundingMode.HALF_UP),
                START_TIME,
                END_TIME
        );
    }

    public static RideUpdatingDto buildRideUpdatingDto() {
        return new RideUpdatingDto(
                UPDATED_PASSENGER_ID,
                UPDATED_PROMO_CODE,
                UPDATED_SERVICE_CATEGORY,
                UPDATED_STATUS,
                UPDATED_PAYMENT_METHOD,
                UPDATED_PICKUP_ADDRESS,
                UPDATED_DESTINATION_ADDRESS,
                UPDATED_COST.setScale(2, RoundingMode.HALF_UP),
                UPDATED_START_TIME,
                UPDATED_END_TIME);
    }

    public static RideBookingAddingDto buildBookingAddingDto() {
        return new RideBookingAddingDto(
                PASSENGER_ID,
                SERVICE_CATEGORY,
                PAYMENT_METHOD,
                PICKUP_ADDRESS,
                DESTINATION_ADDRESS);
    }

    public static RideBookingUpdatingDto buildBookingUpdatingDto() {
        return new RideBookingUpdatingDto(
                UPDATED_PASSENGER_ID,
                UPDATED_PAYMENT_METHOD,
                UPDATED_PICKUP_ADDRESS,
                UPDATED_DESTINATION_ADDRESS);
    }
}

