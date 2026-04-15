package com.example.px_service.common.code;

public enum ResultCode {

    SUCCESS(0, "success"),
    ERROR(500, "error"),
    NOT_FOUND(404, "not found"),
    PARAM_ERROR(400, "parameter error");
    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
