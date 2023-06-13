package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;


public class AlreadyExistsException extends BaseServiceException {
    public AlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
