package com.cabaggregator.promocodeservice.integration.repository;

import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.cabaggregator.promocodeservice.util.PromoStatTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PromoStatRepositoryTest {
    @Autowired
    private PromoStatRepository promoStatRepository;

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("promo_code_database")
            .withUsername("postgres")
            .withPassword("root");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Test
    void existsByPromoCodeAndUserId_ShouldReturnTrue_WhenPromoStatExists() {
        final PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        final PromoCode savedPromoCode = promoCodeRepository.save(promoCode);

        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().id(null).build();
        promoStat.setPromoCode(savedPromoCode);
        promoStatRepository.save(promoStat);

        boolean result = promoStatRepository.existsByPromoCodeAndUserId(promoStat.getPromoCode(), promoStat.getUserId());

        assertThat(result).isTrue();
    }

    @Test
    void existsByPromoCodeAndUserId_ShouldReturnFalse_WhenPromoStatDoesNotExist() {
        final PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();

        boolean result = promoStatRepository.existsByPromoCodeAndUserId(promoStat.getPromoCode(), promoStat.getUserId());

        assertThat(result).isFalse();
    }
}
