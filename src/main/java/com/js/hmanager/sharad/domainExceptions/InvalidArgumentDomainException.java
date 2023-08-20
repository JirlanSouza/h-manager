package com.js.hmanager.sharad.domainExceptions;

public class InvalidArgumentDomainException extends RuntimeException {
    public InvalidArgumentDomainException(String message) {
        super(message);
    }

    public InvalidArgumentDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
