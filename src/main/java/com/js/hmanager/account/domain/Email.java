package com.js.hmanager.account.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;

public record Email(String value) {
    public Email(String value) {
        if (value == null) {
            throw new InvalidArgumentDomainException("The email cannot be null");
        }

        this.value = value.trim();

        if (this.value.isBlank() && this.value.length() < 3) {
            throw new InvalidArgumentDomainException("The email is invalid");
        }
    }
}
