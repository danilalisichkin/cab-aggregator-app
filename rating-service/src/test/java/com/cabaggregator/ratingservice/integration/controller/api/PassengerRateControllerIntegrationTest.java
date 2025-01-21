package com.cabaggregator.ratingservice.integration.controller.api;

import com.cabaggregator.ratingservice.config.AbstractMongoIntegrationTest;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.util.JsonReader;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import com.cabaggregator.ratingservice.util.RestAssuredSpec;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

import static com.cabaggregator.ratingservice.util.IntegrationTestUtil.LOCAL_HOST;
import static com.cabaggregator.ratingservice.util.IntegrationTestUtil.PASSENGER_RATES_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PassengerRateControllerIntegrationTest extends AbstractMongoIntegrationTest {

    @Autowired
    private PassengerRateRepository passengerRateRepository;

    @Autowired
    private RestAssuredSpec restAssuredSpec;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @PostConstruct
    void initBaseUrl() {
        this.baseUrl = "%s:%s/%s".formatted(LOCAL_HOST, port, PASSENGER_RATES_BASE_URL);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = baseUrl;
    }

    @AfterEach
    void clearPassengerRates() {
        passengerRateRepository.deleteAll();
    }

    private void importPassengerRates() throws IOException {
        List<PassengerRate> data = JsonReader.readValues("/mongodb/passenger_rates.json", PassengerRate.class);

        passengerRateRepository.saveAll(data);
    }

    private void importNotSetPassengerRates() throws IOException {
        List<PassengerRate> data = JsonReader.readValues("/mongodb/not_set_passenger_rates.json", PassengerRate.class);

        passengerRateRepository.saveAll(data);
    }

    @Test
    @SneakyThrows
    void getPassengerRating_ShouldReturnAveragePassengerRating_WhenPassengerWasEverRated() {
        importPassengerRates();

        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .when()
                .get("/{passengerId}/average")
                .then()
                .statusCode(200)
                .body(equalTo(PassengerRateTestUtil.AVERAGE_RATING.toString()));
    }

    @Test
    void getPassengerRating_ShouldReturnNotFoundStatus_WhenPassengerWasNeverRated() {
        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .when()
                .get("/{passengerId}/average")
                .then()
                .statusCode(404);
    }

    @Test
    @SneakyThrows
    void getPageOfPassengerRates_ShouldReturnPageDto_WhenUserIsRequestedPassenger() {
        importPassengerRates();

        int expectedPage = 0;
        int expectedPageSize = 10;
        int expectedTotalPages = 1;
        int expectedContentSize = 2;

        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .queryParam("offset", "0")
                .queryParam("limit", "10")
                .queryParam("sortBy", "ID")
                .queryParam("sortOrder", "ASC")
                .when()
                .get("/{passengerId}")
                .then()
                .statusCode(200)
                .body("page", equalTo(expectedPage))
                .body("pageSize", equalTo(expectedPageSize))
                .body("totalPages", equalTo(expectedTotalPages))
                .body("content", hasSize(expectedContentSize))
                .body("content.passengerId", everyItem(equalTo(PassengerRateTestUtil.PASSENGER_ID.toString())));
    }

    @Test
    @SneakyThrows
    void getPassengerRate_ShouldReturnPassengerRate_WhenPassengerRateFound() {
        importPassengerRates();

        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .pathParam("rideId", PassengerRateTestUtil.RIDE_ID.toString())
                .when()
                .get("/{passengerId}/ride/{rideId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(PassengerRateTestUtil.ID.toString()));
    }

    @Test
    @SneakyThrows
    void getPassengerRate_ShouldReturnForbiddenStatus_WhenUserIsAnotherPassenger() {
        importPassengerRates();

        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .pathParam("passengerId", DriverRateTestUtil.OTHER_PASSENGER_ID.toString())
                .pathParam("rideId", PassengerRateTestUtil.OTHER_RIDE_ID.toString())
                .when()
                .get("/{passengerId}/ride/{rideId}")
                .then()
                .statusCode(403);
    }

    @Test
    void getPassengerRate_ShouldReturnNotFoundStatus_WhenPassengerRateNotFound() {
        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .pathParam("rideId", PassengerRateTestUtil.NOT_EXISTING_RIDE_ID.toString())
                .when()
                .get("/{passengerId}/ride/{rideId}")
                .then()
                .statusCode(404);
    }

    @Test
    void savePassengerRate_ShouldReturnPassengerRate_WhenPassengerRateIsValid() {
        PassengerRateAddingDto passengerRateAddingDto = PassengerRateTestUtil.buildPassengerRateAddingDto();
        UUID passengerId = passengerRateAddingDto.passengerId();
        UUID driverId = passengerRateAddingDto.driverId();
        ObjectId rideId = passengerRateAddingDto.rideId();

        given()
                .contentType(ContentType.JSON)
                .body(passengerRateAddingDto)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("passengerId", equalTo(passengerId.toString()))
                .body("driverId", equalTo(driverId.toString()))
                .body("rideId", equalTo(rideId.toString()));

        Optional<PassengerRate> passengerRate = passengerRateRepository.findByPassengerIdAndRideId(passengerId, rideId);
        assertThat(passengerRate).isPresent();
        assertThat(passengerRate.get().getPassengerId()).isEqualTo(passengerId);
        assertThat(passengerRate.get().getDriverId()).isEqualTo(driverId);
        assertThat(passengerRate.get().getRideId()).isEqualTo(rideId);
    }

    @Test
    @SneakyThrows
    void savePassengerRate_ShouldReturnConflictStatus_WhenPassengerRateIsNotUnique() {
        importPassengerRates();

        PassengerRateAddingDto passengerRateAddingDto = PassengerRateTestUtil.buildPassengerRateAddingDto();
        int expectedPassengerRatesSize = 3;

        given()
                .contentType(ContentType.JSON)
                .body(passengerRateAddingDto)
                .when()
                .post()
                .then()
                .statusCode(409);

        List<PassengerRate> passengerRates = passengerRateRepository.findAll();
        assertThat(passengerRates).hasSize(expectedPassengerRatesSize);
    }

    @Test
    @SneakyThrows
    void setPassengerRate_ShouldReturnPassengerRate_WhenPassengerRateFoundAndIsNotSet() {
        importNotSetPassengerRates();

        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();

        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .body(passengerRateSettingDto)
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .pathParam("rideId", PassengerRateTestUtil.RIDE_ID.toString())
                .when()
                .put("/{passengerId}/ride/{rideId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(PassengerRateTestUtil.ID.toString()))
                .body("passengerId", equalTo(PassengerRateTestUtil.PASSENGER_ID.toString()))
                .body("passengerId", equalTo(PassengerRateTestUtil.PASSENGER_ID.toString()))
                .body("rideId", equalTo(PassengerRateTestUtil.RIDE_ID.toString()))
                .body("rate", equalTo(PassengerRateTestUtil.RATE));
    }

    @Test
    @SneakyThrows
    void setPassengerRate_ShouldReturnNotFoundStatus_WhenPassengerRateNotFound() {
        importPassengerRates();

        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();

        given()
                .spec(restAssuredSpec.getPassengerAuthSpec())
                .body(passengerRateSettingDto)
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .pathParam("rideId", PassengerRateTestUtil.NOT_EXISTING_RIDE_ID.toString())
                .when()
                .put("/{passengerId}/ride/{rideId}")
                .then()
                .statusCode(404);
    }

    @Test
    @SneakyThrows
    void setPassengerRate_ShouldReturnBadRequestStatus_WhenPassengerRateAlreadySet() {
        importPassengerRates();

        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();

        given()
                .spec(restAssuredSpec.getDriverAuthSpec())
                .body(passengerRateSettingDto)
                .pathParam("passengerId", PassengerRateTestUtil.PASSENGER_ID.toString())
                .pathParam("rideId", PassengerRateTestUtil.RIDE_ID.toString())
                .when()
                .put("/{passengerId}/ride/{rideId}")
                .then()
                .statusCode(400);
    }
}
