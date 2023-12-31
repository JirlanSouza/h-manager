package com.js.hmanager.account.authentication;

import io.restassured.response.Response;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthenticationControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthenticationTestUtils authenticationTestUtils;

    @Autowired
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Should be create token")
    public void createToken() {
        var email = "test_user@hmanger.com.br";
        var password = "Test%password0";

        this.authenticationTestUtils.createUser(email, password);

        Response response = given().basePath("/auth/token")
                .port(port)
                .auth().preemptive().basic(email, password)
                .when()
                .post();


        AuthenticationTokenResponse tokenResponse = response.body().as(AuthenticationTokenResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(tokenResponse.token()).isNotBlank();
        assertThat(tokenResponse.user()).isEqualTo(email);

        Jwt jwt = this.jwtDecoder.decode(tokenResponse.token());
        assertThat(jwt.<String>getClaim("sub")).isEqualTo(email);
        assertThat(jwt.getExpiresAt()).isAfter(Instant.now());
    }

    @ParameterizedTest()
    @ValueSource(booleans = {true, false})
    @DisplayName("Should be return Unauthorized http status on authenticate with wrong email or password")
    public void unauthorizedHttpStatus(boolean wrongEmail) {
        var email = "test_user@hmanger.com.br";
        var password = "Test%password0";

        this.authenticationTestUtils.createUser(email, password);

        if (wrongEmail) {
            email = "wrong@hmanager.com.br";
        } else {
            password = "WrongPassword@1";
        }


        given().basePath("/auth/token")
                .port(port)
                .auth().preemptive().basic(email, password)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}