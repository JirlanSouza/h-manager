package com.js.hmanager.account.domain;

import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final UUID id;
    private String firstName;
    private String lastName;
    private UserName userName;
    private Password password;
    private UserType userType;

    public User(String firstName, String lastName, UserName userName, Password password, UserType userType) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public static User restore(
            UUID id,
            String firstName,
            String lastName,
            UserName userName,
            Password password,
            UserType userType
    ) {
        return new User(id, firstName, lastName, userName, password, userType);
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new InvalidArgumentDomainException("The first name cannot be null");
        }

        this.firstName = firstName.trim();

        if (this.firstName.isEmpty() || this.firstName.length() < 3) {
            throw new InvalidArgumentDomainException("The first name have 3 or more characters");
        }

    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new InvalidArgumentDomainException("The last name cannot be null");
        }

        this.lastName = lastName.trim();

        if (this.lastName.isEmpty() || this.lastName.length() < 3) {
            throw new InvalidArgumentDomainException("The last name have 3 or more characters");
        }
    }
}
