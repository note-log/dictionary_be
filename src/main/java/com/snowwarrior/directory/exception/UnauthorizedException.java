package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseServiceException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
