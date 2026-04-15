package com.example.px_service.common.response;

import com.example.px_service.common.enums.ResultCode;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private long timestamp;

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        ApiResponse.messageSource = messageSource;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ApiResponse<T> success(T data) {
        Locale localeNew = LocaleContextHolder.getLocale();  // 自动获取当前请求的 Locale
        String key = ResultCode.SUCCESS.getMessage();  // 看看这个是什么
        String message = getMessage(key, localeNew);

        return new ApiResponse<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(message, null);
    }

    public static <T> ApiResponse<T> error(String message, Integer code) {
        int finalCode = (code == null) ? ResultCode.ERROR.getCode() : code;
        return new ApiResponse<>(finalCode, message, null);
    }

    public static <T> ApiResponse<T> errorByKey(String key) {
        return errorByKey(key, null);
    }

    public static <T> ApiResponse<T> errorByKey(String key, Integer code) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = getMessage(key, locale);
        return error(message, code);
    }

    private static String getMessage(String key, Locale locale) {
        try {
            System.out.println("当前 locale: " + locale);  // 打印看看
            if (messageSource == null) {
                return key;
            }
            return messageSource.getMessage(key, null, locale);
        } catch (Exception e) {
            System.out.println("获取失败，key: " + key + ", locale: " + locale);  // 打印失败原因

            return key;
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
