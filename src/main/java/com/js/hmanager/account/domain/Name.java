package com.js.hmanager.account.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;


public record Name(String firstName, String lastName) {
    public Name(String firstName, String lastName) {
        if (firstName == null) {
            throw new InvalidArgumentDomainException("The first name cannot be null");
        }

        this.firstName = firstName.trim();

        if (this.firstName.isEmpty() || this.firstName.length() < 3) {
            throw new InvalidArgumentDomainException("The first name have 3 or more characters");
        }

        if (lastName == null) {
            throw new InvalidArgumentDomainException("The last name cannot be null");
        }

        this.lastName = lastName.trim();

        if (this.lastName.isEmpty() || this.lastName.length() < 3) {
            throw new InvalidArgumentDomainException("The last name have 3 or more characters");
        }

    }
}
