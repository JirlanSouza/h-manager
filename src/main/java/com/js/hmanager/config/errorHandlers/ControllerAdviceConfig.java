package com.js.hmanager.config.errorHandlers;

import com.js.hmanager.sharad.domainExceptions.ConflictEntityDomainException;
import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler({InvalidArgumentDomainException.class,})
    public ResponseEntity<ProblemDetail> handleBadRequest(RuntimeException ex, WebRequest req) {
        return makeErrorResponse(HttpStatus.BAD_REQUEST, ex, req);
    }

    @ExceptionHandler(ConflictEntityDomainException.class)
    public ResponseEntity<ProblemDetail> handleConflict(RuntimeException ex, WebRequest req) {
        return makeErrorResponse(HttpStatus.CONFLICT, ex, req);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageError(RuntimeException ex, WebRequest req) {
        return makeErrorResponse(HttpStatus.BAD_REQUEST, new RuntimeException("Invalid request payload"), req);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleAll(RuntimeException ex, WebRequest req) {
        System.out.println(ex.getClass());
        return makeErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, req);
    }

    private ResponseEntity<ProblemDetail> makeErrorResponse(HttpStatus status, RuntimeException ex, WebRequest req) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setDetail(ex.getMessage());

        return ResponseEntity.of(problemDetail).build();
    }
}
