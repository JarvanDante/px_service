package com.example.px_service.common.exception;

import com.example.px_service.common.code.BizCode;

import java.util.Objects;

public class BizException extends RuntimeException {

    private final BizCode bizCode;

    public BizException(BizCode bizCode) {
        //super(messageKey) 是在调用父类 RuntimeException 的构造方法
        super(requireMessageKey(bizCode));
        this.bizCode = bizCode;
    }

    private static String requireMessageKey(BizCode bizCode) {
        return Objects.requireNonNull(bizCode, "errorCode must not be null").getMessageKey();
    }

    public BizCode getErrorCode() {
        return bizCode;
    }

    public int getCode() {
        return bizCode.getCode();
    }

    public String getMessageKey() {
        return bizCode.getMessageKey();
    }
}
