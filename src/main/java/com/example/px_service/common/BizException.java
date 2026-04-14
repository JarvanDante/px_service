package com.example.px_service.common;

public class BizException extends RuntimeException {

    private final int code;
    private final String messageKey;

    public BizException(int code, String messageKey) {
        super(messageKey);//super(messageKey) 是在调用父类 RuntimeException 的构造方法
        this.code = code;
        this.messageKey = messageKey;
    }

    public int getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
