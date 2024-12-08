package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RideFare;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideTestUtil {
    public static final ObjectId ID = new ObjectId("60c72b2f9b1e8c001c8d5a3f");
    public static final UUID PASSENGER_ID = UUID.fromString("1000e57c-114a-433d-6ac2-55048a29eab9");
    public static final UUID DRIVER_ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");
    public static final String PROMO_CODE = "PROMO2024";
    public static final RideFare FARE = RideFare.COMFORT;
    public static final RideStatus STATUS = RideStatus.COMPLETED;
    public static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CARD;
    public static final Long DISTANCE = 2400L;
    public static final Long PRICE = 1200L;

    public static final LocalDateTime ORDER_TIME = LocalDateTime.parse("2024-12-01T11:30:00");
    public static final LocalDateTime START_TIME = LocalDateTime.parse("2024-12-01T11:35:00");
    public static final LocalDateTime END_TIME = LocalDateTime.parse("2024-12-01T11:45:00");
    public static final Duration ESTIMATED_DURATION = Duration.ofMinutes(12);

    public static final String PICK_UP_FULL_ADDRESS = "123 N Main St, Los Angeles, CA 90012";
    public static final List<Double> PICK_UP_COORDINATES = List.of(-118.238512, 34.05756);
    public static final String DROP_OFF_FULL_ADDRESS
            = "Los Angeles County Museum of Art, 5905 Wilshire Blvd, Los Angeles, CA 90036";
    public static final List<Double> DROP_OFF_COORDINATES = List.of(-118.358467, 34.063621);

    public static final UUID NOT_EXISTING_PASSENGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final UUID NOT_EXISTING_DRIVER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final RideStatus NOT_EXISTING_STATUS = RideStatus.ARRIVING;

    public static Ride.RideBuilder getRideBuilder() {
        return Ride.builder()
                .id(ID)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .promoCode(PROMO_CODE)
                .fare(FARE)
                .status(STATUS)
                .paymentMethod(PAYMENT_METHOD)
                .pickUpAddress(buildPickUpAddress())
                .dropOffAddress(buildDropOffAddress())
                .distance(DISTANCE)
                .price(PRICE)
                .orderTime(ORDER_TIME)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .estimatedDuration(ESTIMATED_DURATION);
    }

    private static Address buildPickUpAddress() {
        return new Address(
                PICK_UP_FULL_ADDRESS,
                PICK_UP_COORDINATES);
    }

    private static Address buildDropOffAddress() {
        return new Address(
                DROP_OFF_FULL_ADDRESS,
                DROP_OFF_COORDINATES);
    }
}
