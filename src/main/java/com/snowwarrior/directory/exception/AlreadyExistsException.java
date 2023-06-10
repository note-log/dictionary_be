package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;


public class AlreadyExistsException extends ErrorResponseException {

    public AlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, asProblemDetail(message), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, message);
    }
}
