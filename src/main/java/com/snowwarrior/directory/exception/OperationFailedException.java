package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;

public class OperationFailedException extends BaseServiceException {
    public OperationFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "操作失败，请稍后重试");
    }
}
