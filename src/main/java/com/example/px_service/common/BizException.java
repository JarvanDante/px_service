package com.example.px_service.common;

import java.util.Objects;

public class BizException extends RuntimeException {

    private final ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        //super(messageKey) 是在调用父类 RuntimeException 的构造方法
        super(requireMessageKey(errorCode));
        this.errorCode = errorCode;
    }

    private static String requireMessageKey(ErrorCode errorCode) {
        return Objects.requireNonNull(errorCode, "errorCode must not be null").getMessageKey();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getCode() {
        return errorCode.getCode();
    }

    public String getMessageKey() {
        return errorCode.getMessageKey();
    }
}
