package com.cabaggregator.driverservice.integration.repository;

import com.cabaggregator.driverservice.config.AbstractIntegrationTest;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.util.CarTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Sql(scripts = {
        "classpath:/postgresql/import_cars.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private CarRepository carRepository;

    @Test
    void existsByLicensePlate_ShouldReturnTrue_WhenCarWithLicensePlateExists() {
        boolean actual = carRepository.existsByLicensePlate(CarTestUtil.LICENSE_PLATE);

        assertThat(actual).isTrue();
    }

    @Test
    void existsByLicensePlate_ShouldReturnFalse_WhenCarWithLicensePlateDoesNotExist() {
        boolean actual = carRepository.existsByLicensePlate(CarTestUtil.NOT_EXISTING_LICENSE_PLATE);

        assertThat(actual).isFalse();
    }
}
