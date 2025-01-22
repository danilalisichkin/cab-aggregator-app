package com.cabaggregator.ratingservice.integration.controller.api;

import com.cabaggregator.ratingservice.config.AbstractMongoIntegrationTest;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.util.JsonReader;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import com.cabaggregator.ratingservice.util.RestAssuredSpec;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cabaggregator.ratingservice.util.IntegrationTestUtil.DRIVER_RATES_BASE_URL;
import static com.cabaggregator.ratingservice.util.IntegrationTestUtil.LOCAL_HOST;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DriverRateControllerIntegrationTest extends AbstractMongoIntegrationTest {

    @Autowired
    private DriverRateRepository driverRateRepository;

    @Autowired
    private RestAssuredSpec restAssuredSpec;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @PostConstruct
    void initBaseUrl() {
        this.baseUrl = "%s:%s/%s".formatted(LOCAL_HOST, port, DRIVER_RATES_BASE_URL);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = baseUrl;
    }

    @AfterEach
    void clearDriverRates() {
        driverRateRepository.deleteAll();
    }

    private void importDriverRates() throws IOException {
        List<DriverRate> data = JsonReader.readValues("/mongodb/driver_rates.json", DriverRate.class);

        driverRateRepository.saveAll(data);
    }

    private void importNotSetDriverRates() throws IOException {
        List<DriverRate> data = JsonReader.readValues("/mongodb/not_set_driver_rates.json", DriverRate.class);

        driverRateRepository.saveAll(data);
    }

    @Test
    @SneakyThrows
    void getDriverRating_ShouldReturnAverageDriverRating_WhenDriverWasEverRated() {
        importDriverRates();

        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .when()
                .get("/{driverId}/average")
                .then()
                .statusCode(200)
                .body(equalTo(DriverRateTestUtil.AVERAGE_RATING.toString()));
    }

    @Test
    void getDriverRating_ShouldReturnNotFoundStatus_WhenDriverWasNeverRated() {
        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .when()
                .get("/{driverId}/average")
                .then()
                .statusCode(404);
    }

    @Test
    @SneakyThrows
    void getPageOfDriverRates_ShouldReturnPageDto_WhenUserIsRequestedDriver() {
        importDriverRates();

        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 1;
        int expectedContentSize = 2;

        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .queryParam("offset", "0")
                .queryParam("limit", "10")
                .queryParam("sortBy", "ID")
                .queryParam("sortOrder", "ASC")
                .when()
                .get("/{driverId}")
                .then()
                .statusCode(200)
                .body("page", equalTo(expectedPage))
                .body("pageSize", equalTo(expectedPageSize))
                .body("totalPages", equalTo(expectedTotalPages))
                .body("content", hasSize(expectedContentSize))
                .body("content.driverId", everyItem(equalTo(DriverRateTestUtil.DRIVER_ID.toString())));
    }

    @Test
    @SneakyThrows
    void getDriverRate_ShouldReturnDriverRate_WhenDriverRateFound() {
        importDriverRates();

        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .pathParam("rideId", DriverRateTestUtil.RIDE_ID.toString())
                .when()
                .get("/{driverId}/ride/{rideId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(DriverRateTestUtil.ID.toString()));
    }

    @Test
    @SneakyThrows
    void getDriverRate_ShouldReturnForbiddenStatus_WhenUserIsAnotherDriver() {
        importDriverRates();

        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .pathParam("driverId", PassengerRateTestUtil.OTHER_DRIVER_ID.toString())
                .pathParam("rideId", DriverRateTestUtil.OTHER_RIDE_ID.toString())
                .when()
                .get("/{driverId}/ride/{rideId}")
                .then()
                .statusCode(403);
    }

    @Test
    void getDriverRate_ShouldReturnNotFoundStatus_WhenDriverRateNotFound() {
        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .pathParam("rideId", DriverRateTestUtil.NOT_EXISTING_RIDE_ID.toString())
                .when()
                .get("/{driverId}/ride/{rideId}")
                .then()
                .statusCode(404);
    }

    @Test
    void saveDriverRate_ShouldReturnDriverRate_WhenDriverRateIsValid() {
        DriverRateAddingDto driverRateAddingDto = DriverRateTestUtil.buildDriverRateAddingDto();
        UUID driverId = driverRateAddingDto.driverId();
        UUID passengerId = driverRateAddingDto.passengerId();
        ObjectId rideId = driverRateAddingDto.rideId();

        given()
                .spec(restAssuredSpec.getAdminRequestSpec())
                .body(driverRateAddingDto)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("driverId", equalTo(driverId.toString()))
                .body("passengerId", equalTo(passengerId.toString()))
                .body("rideId", equalTo(rideId.toString()));

        Optional<DriverRate> driverRate = driverRateRepository.findByDriverIdAndRideId(driverId, rideId);
        assertThat(driverRate).isPresent();
        assertThat(driverRate.get().getDriverId()).isEqualTo(driverId);
        assertThat(driverRate.get().getRideId()).isEqualTo(rideId);
        assertThat(driverRate.get().getPassengerId()).isEqualTo(passengerId);
    }

    @Test
    @SneakyThrows
    void saveDriverRate_ShouldReturnConflictStatus_WhenDriverRateIsNotUnique() {
        importDriverRates();

        DriverRateAddingDto driverRateAddingDto = DriverRateTestUtil.buildDriverRateAddingDto();
        int expectedDriverRatesSize = 3;

        given()
                .spec(restAssuredSpec.getAdminRequestSpec())
                .body(driverRateAddingDto)
                .when()
                .post()
                .then()
                .statusCode(409);

        List<DriverRate> driverRates = driverRateRepository.findAll();
        assertThat(driverRates).hasSize(expectedDriverRatesSize);
    }

    @Test
    @SneakyThrows
    void setDriverRate_ShouldReturnDriverRate_WhenDriverRateFoundAndIsNotSet() {
        importNotSetDriverRates();

        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();
        List<Integer> feedBackOptionsAsInts =
                DriverRateTestUtil.FEEDBACK_OPTIONS.stream()
                        .map(Enum::ordinal)
                        .toList();

        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .body(driverRateSettingDto)
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .pathParam("rideId", DriverRateTestUtil.RIDE_ID.toString())
                .when()
                .put("/{driverId}/ride/{rideId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(DriverRateTestUtil.ID.toString()))
                .body("driverId", equalTo(DriverRateTestUtil.DRIVER_ID.toString()))
                .body("passengerId", equalTo(DriverRateTestUtil.PASSENGER_ID.toString()))
                .body("rideId", equalTo(DriverRateTestUtil.RIDE_ID.toString()))
                .body("rate", equalTo(DriverRateTestUtil.RATE))
                .body("feedbackOptions", containsInAnyOrder(feedBackOptionsAsInts.toArray()));
    }

    @Test
    @SneakyThrows
    void setDriverRate_ShouldReturnNotFoundStatus_WhenDriverRateNotFound() {
        importDriverRates();

        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();

        given()
                .spec(restAssuredSpec.getAdminRequestSpec())
                .body(driverRateSettingDto)
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .pathParam("rideId", DriverRateTestUtil.NOT_EXISTING_RIDE_ID.toString())
                .when()
                .put("/{driverId}/ride/{rideId}")
                .then()
                .statusCode(404);
    }

    @Test
    @SneakyThrows
    void setDriverRate_ShouldReturnBadRequestStatus_WhenDriverRateAlreadySet() {
        importDriverRates();

        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();

        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .body(driverRateSettingDto)
                .pathParam("driverId", DriverRateTestUtil.DRIVER_ID.toString())
                .pathParam("rideId", DriverRateTestUtil.RIDE_ID.toString())
                .when()
                .put("/{driverId}/ride/{rideId}")
                .then()
                .statusCode(400);
    }
}
