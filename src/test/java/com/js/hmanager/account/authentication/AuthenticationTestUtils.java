package com.js.hmanager.account.authentication;

import com.js.hmanager.account.database.JpaUserRepository;
import com.js.hmanager.account.database.UserModel;
import com.js.hmanager.account.domain.User;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

@Component
public class AuthenticationTestUtils {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    JwtEncoder jwtEncoder;

    public AuthenticationScheme getAuthentication(int port) {
        String token = this.getAccessToken();
        return oauth2(token);
    }

    public void createUser(String email, String password) {
        User user = User.create("Joe", "Jho", email, password, "MANAGER");
        this.jpaUserRepository.save(UserModel.from(user));
    }

    public String getAccessToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user",
                "password");

        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("hmanager-backend")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(claims));

        return jwt.getTokenValue();
    }

    public String getToken(int port) {
        var email = "test_user@hmanger.com.br";
        var password = "Test%password0";

        this.createUser(email, password);

        Response response = given().basePath("/login")
                .port(port)
                .formParam("username", email)
                .formParam("password", password)
                .when()
                .post();

        return response.body().as(AuthenticationTokenResponse.class).token();
    }
}
