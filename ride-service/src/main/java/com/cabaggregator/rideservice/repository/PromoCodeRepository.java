package com.cabaggregator.rideservice.repository;

import com.cabaggregator.rideservice.entity.PromoCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends MongoRepository<PromoCode, ObjectId> {
    Optional<PromoCode> findByValue(String value);
}
