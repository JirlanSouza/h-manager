package com.js.hmanager.account.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final UUID id;
    private Name name;
    private Email email;
    private  Password password;
    private UserType userType;

    public static User create(String firstName, String lastName, String email, String password, String userType) {
        var userId = UUID.randomUUID();
        var name = new Name(firstName, lastName);
        var userEmail = new Email(email);
        var userPassword = new Password(password);
        var userUserType = UserType.valueOf(userType);

        return new User(userId, name, userEmail, userPassword, userUserType);
    }

}
