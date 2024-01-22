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
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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

    private List<UUID> createRoomsIntoDataBase() {
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
        List<UUID> roomsIds = this.createRoomsIntoDataBase();

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
}