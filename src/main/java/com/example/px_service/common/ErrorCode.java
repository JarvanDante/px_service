package com.example.px_service.common;

public enum ErrorCode {

    // 1xxx: 认证(auth)
    //账号或密码错误
    AUTH_INVALID_CREDENTIALS(1001, "auth.invalid.credentials"),
    // 2xxx: 用户(user)
    //用户名已存在
    USERNAME_DUPLICATE(2001, "user.username.duplicate");

    private final int code;
    private final String messageKey;

    ErrorCode(int code, String messageKey) {
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
