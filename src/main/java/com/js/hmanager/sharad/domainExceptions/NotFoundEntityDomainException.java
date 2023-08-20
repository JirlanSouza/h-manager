package com.js.hmanager.sharad.domainExceptions;

public class NotFoundEntityDomainException extends RuntimeException {

    public NotFoundEntityDomainException(String message) {
        super(message);
    }

    public NotFoundEntityDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
