package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RideFare;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideTestUtil {
    public static final ObjectId ID = new ObjectId("60c72b2f9b1e8c001c8d5a3f");
    public static final UUID PASSENGER_ID = UUID.fromString("1000e57c-114a-433d-6ac2-55048a29eab9");
    public static final UUID DRIVER_ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final RideFare FARE = RideFare.COMFORT;
    public static final RideStatus STATUS = RideStatus.COMPLETED;
    public static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CARD;
    public static final RidePaymentStatus PAYMENT_STATUS = RidePaymentStatus.PAID;

    public static final LocalDateTime ORDER_TIME = LocalDateTime.parse("2024-12-01T11:30:00");
    public static final LocalDateTime START_TIME = LocalDateTime.parse("2024-12-01T11:35:00");
    public static final LocalDateTime END_TIME = LocalDateTime.parse("2024-12-01T11:45:00");

    public static final String PICK_UP_FULL_ADDRESS = "123 N Main St, Los Angeles, CA 90012";
    public static final List<Double> PICK_UP_COORDINATES = List.of(-118.238512, 34.05756);
    public static final String DROP_OFF_FULL_ADDRESS
            = "Los Angeles County Museum of Art, 5905 Wilshire Blvd, Los Angeles, CA 90036";
    public static final List<Double> DROP_OFF_COORDINATES = List.of(-118.358467, 34.063621);

    public static final ObjectId NOT_EXISTING_ID = new ObjectId("000000000000000000000000");
    public static final UUID NOT_EXISTING_PASSENGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final UUID NOT_EXISTING_DRIVER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final RideStatus NOT_EXISTING_STATUS = RideStatus.ARRIVING;

    public static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.CASH;
    public static final String UPDATED_PICK_UP_FULL_ADDRESS = "Broadway St, Los Angeles, CA 90012";
    public static final List<Double> UPDATED_PICK_UP_COORDINATES = List.of(-118.24605588892796, 34.05469971000838);

    public static Ride buildDefaultRide() {
        return Ride.builder()
                .id(ID)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .promoCode(PromoCodeTestUtil.VALUE)
                .fare(FARE)
                .status(STATUS)
                .paymentMethod(PAYMENT_METHOD)
                .paymentStatus(PAYMENT_STATUS)
                .pickUpAddress(buildPickUpAddress())
                .dropOffAddress(buildDropOffAddress())
                .distance(RouteTestUtil.DISTANCE)
                .price(PriceTestUtil.PRICE)
                .orderTime(ORDER_TIME)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .estimatedDuration(RouteTestUtil.ESTIMATED_DURATION)
                .build();
    }

    public static Address buildPickUpAddress() {
        return new Address(
                PICK_UP_FULL_ADDRESS,
                PICK_UP_COORDINATES);
    }

    public static Address buildDropOffAddress() {
        return new Address(
                DROP_OFF_FULL_ADDRESS,
                DROP_OFF_COORDINATES);
    }

    public static Address buildUpdatedPickUpAddress() {
        return new Address(
                UPDATED_PICK_UP_FULL_ADDRESS,
                UPDATED_PICK_UP_COORDINATES);
    }

    public static RideDto buildRideDto() {
        return new RideDto(
                ID,
                PASSENGER_ID,
                DRIVER_ID,
                PromoCodeTestUtil.VALUE,
                FARE,
                STATUS,
                PAYMENT_METHOD,
                PAYMENT_STATUS,
                buildPickUpAddress(),
                buildDropOffAddress(),
                RouteTestUtil.DISTANCE,
                PriceTestUtil.PRICE,
                ORDER_TIME,
                START_TIME,
                END_TIME,
                RouteTestUtil.ESTIMATED_DURATION);
    }

    public static RideUpdatingDto buildRideUpdatingDto() {
        return new RideUpdatingDto(
                UPDATED_PAYMENT_METHOD,
                buildUpdatedPickUpAddress(),
                buildDropOffAddress());
    }

    public static RideAddingDto buildRideAddingDto() {
        return new RideAddingDto(
                FARE,
                PAYMENT_METHOD,
                buildPickUpAddress(),
                buildDropOffAddress(),
                PromoCodeTestUtil.VALUE);
    }
}
