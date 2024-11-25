package com.cabaggregator.promocodeservice.repository;

import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PromoStatRepository extends JpaRepository<PromoStat, Long> {
    boolean existsByPromoCodeAndUserId(PromoCode promoCode, UUID userId);
}
