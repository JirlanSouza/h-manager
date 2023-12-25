package com.js.hmanager.common.domainExceptions;

public class NotFoundEntityDomainException extends RuntimeException {

    public NotFoundEntityDomainException(String message) {
        super(message);
    }

    public NotFoundEntityDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
