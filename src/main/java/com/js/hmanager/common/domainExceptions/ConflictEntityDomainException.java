package com.js.hmanager.common.domainExceptions;

public class ConflictEntityDomainException extends RuntimeException {
    public ConflictEntityDomainException(String message) {
        super(message);
    }

    public ConflictEntityDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
