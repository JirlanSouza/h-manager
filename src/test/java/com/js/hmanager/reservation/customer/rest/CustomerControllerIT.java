package com.js.hmanager.reservation.customer.rest;

import com.js.hmanager.account.authentication.AuthenticationTestUtils;
import com.js.hmanager.reservation.customer.data.CustomerJpaRepository;
import com.js.hmanager.reservation.customer.data.CustomerModel;
import com.js.hmanager.common.AbstractApiTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

class CustomerControllerIT extends AbstractApiTest {

    @Autowired
    private AuthenticationTestUtils authenticationTestUtils;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @BeforeEach
    public void prepareDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();

        RestAssured.authentication = this.authenticationTestUtils.getAuthentication(port);
    }

    @AfterAll
    static public void clearDataBase(@Autowired Flyway flyway) {
        flyway.clean();
    }

    @Test
    @DisplayName("Should return customer id")
    public void createCustomer() {
        String createCustomerRequestBody = this.validCreteCustomerRequestBody();

        Response response = given().basePath("/customers")
                .port(port).contentType(ContentType.JSON)
                .body(createCustomerRequestBody)
                .when().post();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.body().as(UUID.class)).isInstanceOf(UUID.class);

        Optional<CustomerModel> customerModelOptional = customerJpaRepository.findById(response.getBody().as(UUID.class));
        assertThat(customerModelOptional.isPresent()).isTrue();

        CustomerModel customerModel = customerModelOptional.get();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(customerModel.getName()).isEqualTo("Joe Jho");
            softly.assertThat((customerModel.getCpf())).isEqualTo("111.444.777-35");
            softly.assertThat(customerModel.getAddressStreet()).isEqualTo("Test street");
            softly.assertThat(customerModel.getAddressNumber()).isEqualTo("999");
            softly.assertThat(customerModel.getAddressNeighborhood()).isEqualTo("Test neighborhood");
            softly.assertThat(customerModel.getAddressZipCode()).isEqualTo("12356-458");
            softly.assertThat(customerModel.getAddressCity()).isEqualTo("Test city");
            softly.assertThat(customerModel.getAddressState()).isEqualTo("Test state");
            softly.assertThat(customerModel.getAddressCountry()).isEqualTo("Test country");
        });
    }

    @Test
    @DisplayName("Should return 400 http status when create invalid customer data")
    public void return404() {
        String createCustomerRequestBody = this.invalidCreteCustomerRequestBody();

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
    public void return409() {
        String createCustomerRequestBody = this.validCreteCustomerRequestBody();

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

    private String validCreteCustomerRequestBody() {
        return """
                {
                    "name": "Joe Jho",
                    "cpf": "111.444.777-35",
                    "email": "joejho@gmail.com",
                    "telephone": "65985890067",
                    "address": {
                        "street": "Test street",
                        "houseNumber": "999",
                        "neighborhood": "Test neighborhood",
                        "zipCode": "12356-458",
                        "city": "Test city",
                        "state": "Test state",
                        "country": "Test country"
                    }
                }
                """;
    }

    private String invalidCreteCustomerRequestBody() {
        return """
                {
                    "name": "Joe Jho",
                    "cpf": "111.222.333-12",
                    "email": "joejho@gmail.com",
                    "telephone": "65985890067",
                    "address": {
                        "street": "Test street",
                        "houseNumber": "999",
                        "neighborhood": "Test neighborhood",
                        "zipCode": "12356-458",
                        "city": "Test city",
                        "state": "Test state",
                        "country": "Test country"
                    }
                }
                """;
    }

}
