package com.js.hmanager.sharad.domainExceptions;

public class InvalidArgumentDomainException extends RuntimeException {
    public InvalidArgumentDomainException() {
    }

    public InvalidArgumentDomainException(String message) {
        super(message);
    }

    public InvalidArgumentDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentDomainException(Throwable cause) {
        super(cause);
    }

    public InvalidArgumentDomainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
