package com.snowwarrior.directory.exception;

public class OperationFailedException extends BaseServiceException{
    public OperationFailedException() {
        super("操作失败，请稍后重试");
    }
}
