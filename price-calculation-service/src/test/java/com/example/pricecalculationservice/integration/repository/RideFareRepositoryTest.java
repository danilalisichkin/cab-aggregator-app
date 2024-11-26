package com.example.pricecalculationservice.integration.repository;

import com.example.pricecalculationservice.config.PostgreSQLContainerConfig;
import com.example.pricecalculationservice.entity.RideFare;
import com.example.pricecalculationservice.repository.RideFareRepository;
import com.example.pricecalculationservice.util.RideFareTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
@ContextConfiguration(classes = PostgreSQLContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RideFareRepositoryTest {
    @Autowired
    private RideFareRepository rideFareRepository;

    @Test
    void findByFareName_ShouldReturnRideFare_WhenRideFareExists() {
        RideFare rideFare = RideFareTestUtil.getRideFareBuilder()
                .id(null)
                .build();
        rideFareRepository.save(rideFare);

        Optional<RideFare> actual = rideFareRepository.findByFareName(rideFare.getFareName());

        assertThat(actual)
                .isNotEmpty()
                .contains(rideFare);
    }

    @Test
    void findByFareName_ShouldReturnEmptyOptional_WhenRideFareDoesNotExist() {
        Optional<RideFare> actual = rideFareRepository.findByFareName(RideFareTestUtil.FARE_NAME);

        assertThat(actual).isEmpty();
    }
}
