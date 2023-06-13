package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;

public class BannedException extends BaseServiceException {
    public BannedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
