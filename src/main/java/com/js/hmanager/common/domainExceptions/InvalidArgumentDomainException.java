package com.js.hmanager.common.domainExceptions;

public class InvalidArgumentDomainException extends RuntimeException {
    public InvalidArgumentDomainException(String message) {
        super(message);
    }

    public InvalidArgumentDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
