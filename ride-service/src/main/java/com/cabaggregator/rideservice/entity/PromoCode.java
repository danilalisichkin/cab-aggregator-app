package com.cabaggregator.rideservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @Indexed(unique = true)
    private String value;

    private Integer discount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
