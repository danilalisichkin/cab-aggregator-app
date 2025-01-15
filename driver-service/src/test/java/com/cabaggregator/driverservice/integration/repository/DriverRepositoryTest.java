package com.cabaggregator.driverservice.integration.repository;

import com.cabaggregator.driverservice.config.AbstractPostgresIntegrationTest;
import com.cabaggregator.driverservice.repository.DriverRepository;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.cabaggregator.driverservice.util.DriverTestUtil;
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
        "classpath:/sql.repository/import_cars.sql",
        "classpath:/sql.repository/import_drivers.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverRepositoryTest extends AbstractPostgresIntegrationTest {
    @Autowired
    private DriverRepository driverRepository;

    @Test
    void existsByPhoneNumber_ShouldReturnTrue_WhenDriverWithPhoneNumberExists() {
        boolean actual = driverRepository.existsByPhoneNumber(DriverTestUtil.PHONE_NUMBER);

        assertThat(actual).isTrue();
    }

    @Test
    void existsByPhoneNumber_ShouldReturnFalse_WhenDriverWithPhoneNumberDoesNotExist() {
        boolean actual = driverRepository.existsByPhoneNumber(DriverTestUtil.NOT_EXISTING_PHONE_NUMBER);

        assertThat(actual).isFalse();
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenDriverWithEmailExists() {
        boolean actual = driverRepository.existsByEmail(DriverTestUtil.EMAIL);

        assertThat(actual).isTrue();
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenDriverWithEmailDoesNotExist() {
        boolean actual = driverRepository.existsByEmail(DriverTestUtil.NOT_EXISTING_EMAIL);

        assertThat(actual).isFalse();
    }

    @Test
    void existsByCarId_ShouldReturnTrue_WhenDriverWithCarIdExists() {
        boolean actual = driverRepository.existsByCarId(CarTestUtil.ID);

        assertThat(actual).isTrue();
    }

    @Test
    void existsByCarId_ShouldReturnFalse_WhenDriverWithCarIdDoesNotExist() {
        boolean actual = driverRepository.existsByCarId(DriverTestUtil.NOT_EXISTING_CAR_ID);

        assertThat(actual).isFalse();
    }
}
