package com.js.hmanager.booking.infra.customer.rest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomerControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Should return customer id")
    void createCustomer() {
        String createCustomerRequestBody = """
                {
                    "name": "Joe Jho",
                        "cpf": "111.444.777-35",
                        "address": {
                            "street": "Test street",
                            "number": "999",
                            "neighborhood": "Test neighborhood",
                            "zipCode": "12356-458",
                            "city": "Test city",
                            "state": "Test state",
                            "country": "Test country"
                        }
                }
                """;

        Response response = given().basePath("/customers")
                .port(port).contentType(ContentType.JSON)
                .body(createCustomerRequestBody)
                .when().post();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.body().as(UUID.class)).isInstanceOf(UUID.class);
    }

    @Test
    @DisplayName("Should return 404 http status when create invalid customer data")
    void return404() {
        String createCustomerRequestBody = """
                {
                    "name": "Joe Jho",
                        "cpf": "111.222.333-12",
                        "address": {
                            "street": "Test street",
                            "number": "999",
                            "neighborhood": "Test neighborhood",
                            "zipCode": "12356-458",
                            "city": "Test city",
                            "state": "Test state",
                            "country": "Test country"
                        }
                }
                """;

        given().basePath("/customers")
                .port(port).contentType(ContentType.JSON)
                .body(createCustomerRequestBody)
                .when().post()
                .then().statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("detail", not(blankString()));
    }

    @Test
    @DisplayName("Should return 409 http status when create customer with existent cpf")
    void return409() {
        String createCustomerRequestBody = """
                {
                    "name": "Joe Jho",
                        "cpf": "111.444.777-35",
                        "address": {
                            "street": "Test street",
                            "number": "999",
                            "neighborhood": "Test neighborhood",
                            "zipCode": "12356-458",
                            "city": "Test city",
                            "state": "Test state",
                            "country": "Test country"
                        }
                }
                """;

        given().basePath("/customers").contentType(ContentType.JSON)
                .port(port).body(createCustomerRequestBody).post();

        given().basePath("/customers")
                .port(port).contentType(ContentType.JSON)
                .body(createCustomerRequestBody)
                .when().post()
                .then().statusCode(HttpStatus.CONFLICT.value())
                .body("status", equalTo(HttpStatus.CONFLICT.value()))
                .body("detail", not(blankString())).log();
    }

}