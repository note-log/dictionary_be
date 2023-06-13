package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;

public class NotAuditException extends BaseServiceException {
    public NotAuditException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
