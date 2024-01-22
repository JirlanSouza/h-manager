package com.js.hmanager.booking.booking.rest;

import com.js.hmanager.account.authentication.AuthenticationTestUtils;
import com.js.hmanager.booking.booking.application.CreateBookingDto;
import com.js.hmanager.booking.customer.data.CustomerJpaRepository;
import com.js.hmanager.booking.customer.data.CustomerModel;
import com.js.hmanager.inventory.data.RoomJpaRepository;
import com.js.hmanager.inventory.data.RoomModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookingControllerIT {

    @LocalServerPort
    int port;

    @Autowired
    AuthenticationTestUtils authenticationTestUtils;

    @Autowired
    CustomerJpaRepository customerRepository;

    @Autowired
    RoomJpaRepository roomRepository;

    @BeforeEach
    void prepareDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();

        RestAssured.authentication = this.authenticationTestUtils.getAuthentication(port);
    }

    private UUID createCustomerIntoDatabase() {
        CustomerModel customerModel = CustomerModel.builder()
                .id(UUID.randomUUID())
                .name("Joe Jho")
                .cpf("111.444.777-35")
                .email("joejho@gmail.com")
                .telephone("65985890067")
                .addressStreet("Test street")
                .addressNumber("999")
                .addressNeighborhood("Test neighborhood")
                .addressZipCode("12356-458")
                .addressCity("Test city")
                .addressState("Test state")
                .addressCountry("Test country")
                .build();

        customerRepository.save(customerModel);

        return customerModel.getId();
    }

    private List<UUID> createRoomsIntoDatabase() {
        RoomModel roomModel = RoomModel.builder()
                .id(UUID.randomUUID())
                .number("1010")
                .doubleBeds(1)
                .singleBeds(0)
                .dailyRate(BigDecimal.valueOf(220.00))
                .available(true)
                .build();

        roomRepository.save(roomModel);

        return List.of(roomModel.getId());
    }

    @Test
    @DisplayName("Should create new booking")
    void createBooking() {
        UUID customerId = this.createCustomerIntoDatabase();
        List<UUID> roomsIds = this.createRoomsIntoDatabase();

        CreateBookingDto bookingDto = new CreateBookingDto(
                customerId,
                OffsetDateTime.now().plusDays(6),
                OffsetDateTime.now().plusDays(10),
                roomsIds
        );

        Response response = given().basePath("/bookings")
                .port(port)
                .contentType(ContentType.JSON)
                .body(bookingDto)
                .when().post();

        assertThat(response.statusCode()).as("Status code is 201 - CREATED").isEqualTo(201);
        assertThat(response.body().as(UUID.class)).as("Response body is UUID").isInstanceOf(UUID.class);
    }

    @Test
    @DisplayName("Should return 404 NOT FOUND status code when create booking to non existent customer")
    void createBookingToNonExistentCustomer() {
        List<UUID> roomsIds = this.createRoomsIntoDatabase();

        CreateBookingDto bookingDto = new CreateBookingDto(
                UUID.randomUUID(),
                OffsetDateTime.now().plusDays(6),
                OffsetDateTime.now().plusDays(10),
                roomsIds
        );

        Response response = given().basePath("/bookings")
                .port(port)
                .contentType(ContentType.JSON)
                .body(bookingDto)
                .when().post();

        assertThat(response.statusCode()).as("Status code is 404 - NOT FOUND").isEqualTo(404);
    }

    @Test
    @DisplayName("Should return 404 NOT FOUND status code when create booking with non existent rooms")
    void createBookingWithNonExistentRooms() {
        UUID customerId = this.createCustomerIntoDatabase();

        List<UUID> roomsIds = new ArrayList<>(this.createRoomsIntoDatabase());
        roomsIds.add(UUID.randomUUID());


        CreateBookingDto bookingDto = new CreateBookingDto(
                customerId,
                OffsetDateTime.now().plusDays(6),
                OffsetDateTime.now().plusDays(10),
                roomsIds
        );

        Response response = given().basePath("/bookings")
                .port(port)
                .contentType(ContentType.JSON)
                .body(bookingDto)
                .when().post();

        assertThat(response.statusCode()).as("Status code is 404 - NOT FOUND").isEqualTo(404);


        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.body().as(ProblemDetail.class)).as("Body has the a problem detail")
                    .isInstanceOf(ProblemDetail.class);

            ProblemDetail responseBody = response.body().as(ProblemDetail.class);

            softly.assertThat(responseBody.getDetail()).as("Response problem detail message")
                    .isEqualTo("The rooms withs ids: %s does not exists".formatted(roomsIds.get(1)));

        });
    }
}