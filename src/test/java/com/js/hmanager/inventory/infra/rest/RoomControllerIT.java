package com.js.hmanager.inventory.infra.rest;

import com.js.hmanager.account.authentication.AuthenticationTestUtils;
import com.js.hmanager.inventory.infra.data.RoomJpaRepository;
import com.js.hmanager.inventory.infra.data.RoomModel;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RoomControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    private AuthenticationTestUtils authenticationTestUtils;

    @Autowired
    private RoomJpaRepository roomRepository;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();

        RestAssured.authentication = this.authenticationTestUtils.getAuthentication(port);
    }

    @AfterAll
    static void clearDataBase(@Autowired Flyway flyway) {
        flyway.clean();
    }

    @Test
    @DisplayName("Should return room id")
    void createCustomer() {
        String createRoomRequestBody = """
                {
                    "number": "1010",
                    "doubleBeds": 1,
                    "singleBeds": 0,
                    "dailyRate": "200.00",
                    "available": true
                }
                """;

        Response response = given().basePath("/rooms")
                .port(port).contentType(ContentType.JSON)
                .body(createRoomRequestBody)
                .when().post();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.body().as(UUID.class)).isInstanceOf(UUID.class);

        Optional<RoomModel> roomModelOptional = roomRepository.findById(response.getBody().as(UUID.class));

        assertThat(roomModelOptional.isPresent()).isTrue();

        RoomModel roomModel = roomModelOptional.get();

        assertThat(roomModel.getNumber()).isEqualTo("1010");
        assertThat(roomModel.getDoubleBeds()).isEqualTo(1);
        assertThat(roomModel.getSingleBeds()).isEqualTo(0);
        assertThat(roomModel.getDailyRate().floatValue()).isEqualTo(200.00f);
        assertThat(roomModel.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Should return 400 http status code when create room with invalid data")
    void createCustomerWithInvalidData() {
        String createRoomRequestBody = """
                {
                    "number": "",
                    "doubleBeds": 1,
                    "singleBeds": 0,
                    "dailyRate": "200.00",
                    "available": true
                }
                """;

        given().basePath("/rooms")
                .port(port).contentType(ContentType.JSON)
                .body(createRoomRequestBody)
                .when().post()
                .then().statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("detail", not(blankString()));
    }

    @Test
    @DisplayName("Should return 409 http status code when create room with existent number")
    void createCustomerWithExistentNumber() {
        String createRoomRequestBody = """
                {
                    "number": "1010",
                    "doubleBeds": 1,
                    "singleBeds": 0,
                    "dailyRate": "200.00",
                    "available": true
                }
                """;

        given().basePath("/rooms")
                .port(port).contentType(ContentType.JSON)
                .body(createRoomRequestBody).post();

        given().basePath("/rooms")
                .port(port).contentType(ContentType.JSON)
                .body(createRoomRequestBody)
                .when().post()
                .then().statusCode(HttpStatus.CONFLICT.value())
                .body("status", equalTo(HttpStatus.CONFLICT.value()))
                .body("detail", not(blankString()));
    }
}