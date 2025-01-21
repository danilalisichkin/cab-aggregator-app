package com.cabaggregator.promocodeservice.integration.repository;

import com.cabaggregator.promocodeservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.cabaggregator.promocodeservice.util.PromoStatTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Sql(scripts = {
        "classpath:/postgresql/import_promo_codes.sql",
        "classpath:/postgresql/import_promo_stats.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PromoStatRepositoryTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private PromoStatRepository promoStatRepository;

    @Test
    void existsByPromoCodeAndUserId_ShouldReturnTrue_WhenPromoStatExists() {
        PromoStat promoStat = PromoStatTestUtil.buildDefaultPromoStat();

        boolean result = promoStatRepository.existsByPromoCodeAndUserId(promoStat.getPromoCode(), promoStat.getUserId());

        assertThat(result).isTrue();
    }

    @Test
    void existsByPromoCodeAndUserId_ShouldReturnFalse_WhenPromoStatDoesNotExist() {
        PromoCode promoCode = PromoCodeTestUtil.buildDefaultPromoCode();
        UUID userId = PromoStatTestUtil.OTHER_USER_ID;

        boolean result = promoStatRepository.existsByPromoCodeAndUserId(promoCode, userId);

        assertThat(result).isFalse();
    }
}
