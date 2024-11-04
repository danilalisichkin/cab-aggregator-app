package com.cabaggregator.rideservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("promo_codes")
public class PromoCode {
    @Id
    private ObjectId id;

    private String value;

    private double discount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
