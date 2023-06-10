package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class UnauthorizedException extends ErrorResponseException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, asProblemDetail(message), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, message);
    }
}
