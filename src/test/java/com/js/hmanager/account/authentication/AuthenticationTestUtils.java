package com.js.hmanager.account.authentication;

import com.js.hmanager.account.database.JpaUserRepository;
import com.js.hmanager.account.database.UserModel;
import com.js.hmanager.account.domain.User;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

@Component
public class AuthenticationTestUtils {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    public AuthenticationScheme getAuthentication(int port) {
        String token = this.getToken(port);
        return oauth2(token);
    }

    public void createUser(String email, String password) {
        User user = User.create("Joe", "Jho", email, password, "MANAGER");
        this.jpaUserRepository.save(UserModel.from(user));
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
