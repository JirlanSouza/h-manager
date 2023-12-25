package com.js.hmanager.account.domain;

import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;

public record UserName(String value) {
    public UserName(String value) {
        if (value == null) {
            throw new InvalidArgumentDomainException("The user name cannot be null");
        }

        this.value = value.trim();

        if (this.value.isBlank() && this.value.length() < 3) {
            throw new InvalidArgumentDomainException("The user name to have 3 or more characters");
        }
    }
}
