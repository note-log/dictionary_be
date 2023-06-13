package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;

public class InvalidOperationException extends BaseServiceException {
    public InvalidOperationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
