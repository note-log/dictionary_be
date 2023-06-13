package com.snowwarrior.directory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

/**
 * 提供一个顶部的web服务异常封装抽象
 */
public abstract class BaseServiceException extends ErrorResponseException {
    public BaseServiceException(String message) {
        super(HttpStatus.OK, asProblemDetail(message), null);
    }

    public BaseServiceException(HttpStatusCode status, String message) {
        super(status, asProblemDetail(status, message), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.OK, message);
    }

    private static ProblemDetail asProblemDetail(HttpStatusCode status, String message) {
        return ProblemDetail.forStatusAndDetail(status, message);
    }
}
