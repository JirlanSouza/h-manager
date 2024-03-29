package com.js.hmanager.reservation.reservation.rest;

import com.js.hmanager.account.authentication.AuthenticationTestUtils;
import com.js.hmanager.common.AbstractApiTest;
import com.js.hmanager.common.data.DataPage;
import com.js.hmanager.inventory.data.RoomModel;
import com.js.hmanager.reservation.reservation.application.CreateReservationDto;
import com.js.hmanager.reservation.reservation.application.ReservationRoomDto;
import com.js.hmanager.reservation.reservation.application.ReservationSummary;
import com.js.hmanager.reservation.reservation.data.ReservationModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationControllerIT extends AbstractApiTest {
    private final String RESERVATIONS_ENDPOINT = "/reservations";

    @Autowired
    private AuthenticationTestUtils authenticationTestUtils;

    @Autowired
    private ReservationTestUtils reservationTestUtils;

    @BeforeEach
    void prepareDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();

        RestAssured.authentication = this.authenticationTestUtils.getAuthentication(port);
    }

    @Test
    @DisplayName("Should create new reservation")
    void createReservation() {
        UUID customerId = this.reservationTestUtils.createCustomerIntoDatabase();
        List<ReservationRoomDto> roomsDto = this.reservationTestUtils.createRoomsIntoDatabase(1).stream()
                .map(rm -> new ReservationRoomDto(
                        rm.getId(),
                        OffsetDateTime.now().plusDays(6),
                        OffsetDateTime.now().plusDays(10)
                )).toList();

        CreateReservationDto reservationDto = new CreateReservationDto(
                customerId,
                roomsDto
        );

        Response response = given().basePath(RESERVATIONS_ENDPOINT)
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post();

        assertThat(response.statusCode()).as("Status code is 201 - CREATED").isEqualTo(201);
        assertThat(response.body().as(UUID.class)).as("Response body is UUID").isInstanceOf(UUID.class);
    }

    @Test
    @DisplayName("Should return 404 NOT FOUND status code when create reservation to non existent customer")
    void createReservationToNonExistentCustomer() {
        List<ReservationRoomDto> roomsDto = this.reservationTestUtils.createRoomsIntoDatabase(1).stream()
                .map(rm -> new ReservationRoomDto(
                        rm.getId(),
                        OffsetDateTime.now().plusDays(6),
                        OffsetDateTime.now().plusDays(10)
                )).toList();

        CreateReservationDto reservationDto = new CreateReservationDto(
                UUID.randomUUID(),
                roomsDto
        );

        Response response = given().basePath(RESERVATIONS_ENDPOINT)
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post();

        assertThat(response.statusCode()).as("Status code is 404 - NOT FOUND").isEqualTo(404);
    }

    @Test
    @DisplayName("Should return 404 NOT FOUND status code when create reservation with non existent rooms")
    void createReservationWithNonExistentRooms() {
        UUID customerId = this.reservationTestUtils.createCustomerIntoDatabase();

        List<ReservationRoomDto> roomsDto = new ArrayList<>(this.reservationTestUtils.createRoomsIntoDatabase(1).stream()
                .map(rm -> new ReservationRoomDto(
                        rm.getId(),
                        OffsetDateTime.now().plusDays(6),
                        OffsetDateTime.now().plusDays(10)
                )).toList());
        roomsDto.add(new ReservationRoomDto(
                UUID.randomUUID(), OffsetDateTime.now().plusDays(6), OffsetDateTime.now().plusDays(10))
        );


        CreateReservationDto reservationDto = new CreateReservationDto(
                customerId,
                roomsDto
        );

        Response response = given().basePath(RESERVATIONS_ENDPOINT)
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post();

        assertThat(response.statusCode()).as("Status code is 404 - NOT FOUND").isEqualTo(404);


        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.body().as(ProblemDetail.class)).as("Body has the a problem detail")
                    .isInstanceOf(ProblemDetail.class);

            ProblemDetail responseBody = response.body().as(ProblemDetail.class);

            softly.assertThat(responseBody.getDetail()).as("Response problem detail message")
                    .isEqualTo("The rooms withs ids: %s does not exists".formatted(roomsDto.get(1).id()));

        });
    }

    @Test
    @DisplayName("Should return 400 BAD REQUEST status code when create reservation with checkin date latter of checkout date")
    void createReservationWithCheckinAfterCheckout() {
        UUID customerId = this.reservationTestUtils.createCustomerIntoDatabase();
        List<ReservationRoomDto> roomsDto = this.reservationTestUtils.createRoomsIntoDatabase(1).stream()
                .map(rm -> new ReservationRoomDto(
                        rm.getId(),
                        OffsetDateTime.now().plusDays(4),
                        OffsetDateTime.now().plusDays(2)
                )).toList();

        CreateReservationDto reservationDto = new CreateReservationDto(
                customerId,
                roomsDto
        );

        Response response = given().basePath(RESERVATIONS_ENDPOINT)
                .port(port)
                .contentType(ContentType.JSON)
                .body(reservationDto)
                .when().post();

        assertThat(response.statusCode()).as("Status code is 409 - BAD REQUEST").isEqualTo(400);


        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.body().as(ProblemDetail.class)).as("Body has the a problem detail")
                    .isInstanceOf(ProblemDetail.class);

            ProblemDetail responseBody = response.body().as(ProblemDetail.class);

            softly.assertThat(responseBody.getDetail()).as("Response problem detail message")
                    .isEqualTo("The hosting period has to be more than 1 day");

        });
    }

    @Test
    @DisplayName("Should return 200 OK status code with page of reservations summary")
    void listReservationsSummary() {
        List<ReservationModel> reservations = this.reservationTestUtils.createReservationsIntoDatabase();

        Response response = given().basePath(RESERVATIONS_ENDPOINT)
                .port(port)
                .accept(ContentType.JSON)
                .when().get();

        assertThat(response.statusCode()).as("Status code is 200 - OK").isEqualTo(200);

        SoftAssertions.assertSoftly(softly -> {
            DataPage<ReservationSummary> dataPage = response.body().<DataPage<ReservationSummary>>as(DataPage.class);
            softly.assertThat(dataPage.totalItems()).isEqualTo(reservations.size());

            List<ReservationSummary> data = response.body().jsonPath().getList("data", ReservationSummary.class);

            softly.assertThat(data.get(0).id()).as("Is equals ids").isEqualTo(reservations.get(0).getId());
            softly.assertThat(data.get(0).roomsAmount()).as("Is equals roomsAmount").isEqualTo(reservations.get(0).getRoms().size());
            softly.assertThat(data.get(0).status()).as("Is equals status").isEqualTo(reservations.get(0).getStatus());
            softly.assertThat(data.get(0).totalPrice()).as("Is equals totalPrice").isEqualTo(reservations.get(0).getTotalPrice());
        });
    }

    @Test
    @DisplayName("Should return 200 OK status code with empty page of reservations summary")
    void listEmptyPageOfReservationsSummary() {
        Response response = given().basePath(RESERVATIONS_ENDPOINT)
                .port(port)
                .accept(ContentType.JSON)
                .when().get();

        assertThat(response.statusCode()).as("Status code is 200 - OK").isEqualTo(200);

        SoftAssertions.assertSoftly(softly -> {
            DataPage<ReservationSummary> dataPage = response.body().<DataPage<ReservationSummary>>as(DataPage.class);
            softly.assertThat(dataPage.totalItems()).as("Page totalItems is 0").isEqualTo(0);

            List<ReservationSummary> data = response.body().jsonPath().getList("data", ReservationSummary.class);
            softly.assertThat(data.isEmpty()).as("Page data is empty").isTrue();
        });
    }
}