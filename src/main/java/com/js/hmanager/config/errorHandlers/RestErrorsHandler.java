package com.js.hmanager.config.errorHandlers;

import com.js.hmanager.common.domainExceptions.ConflictEntityDomainException;
import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestErrorsHandler {

    private final Logger logger = LoggerFactory.getLogger(RestErrorsHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidArgumentDomainException.class,})
    public ResponseEntity<ProblemDetail> handleBadRequest(RuntimeException ex, WebRequest req) {
        return makeErrorResponse(HttpStatus.BAD_REQUEST, ex, req);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictEntityDomainException.class)
    public ResponseEntity<ProblemDetail> handleConflict(RuntimeException ex, WebRequest req) {
        return makeErrorResponse(HttpStatus.CONFLICT, ex, req);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageError(RuntimeException ex, WebRequest req) {
        return makeErrorResponse(HttpStatus.BAD_REQUEST, new RuntimeException("Invalid request payload"), req);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleAll(RuntimeException ex, WebRequest req) {
        this.logger.warn(ex.getMessage());
        return makeErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, req);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorized(RuntimeException ex, WebRequest req) {
        this.logger.info(ex.getMessage());
        return makeErrorResponse(HttpStatus.UNAUTHORIZED, ex, req);
    }

    private ResponseEntity<ProblemDetail> makeErrorResponse(HttpStatus status, RuntimeException ex, WebRequest req) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setDetail(ex.getMessage());

        return ResponseEntity.of(problemDetail).build();
    }
}
