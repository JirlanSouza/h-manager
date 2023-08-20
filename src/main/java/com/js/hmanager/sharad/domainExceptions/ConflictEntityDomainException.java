package com.js.hmanager.sharad.domainExceptions;

public class ConflictEntityDomainException extends RuntimeException {
    public ConflictEntityDomainException(String message) {
        super(message);
    }

    public ConflictEntityDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
