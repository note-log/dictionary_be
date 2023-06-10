package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class NotFoundException extends ErrorResponseException {
    public NotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, asProblemDetail(message), null);
    }
    private static ProblemDetail asProblemDetail(String message) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    }
}