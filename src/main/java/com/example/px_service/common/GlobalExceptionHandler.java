package com.example.px_service.common;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        //获取第一个错误信息
        String errorMsg = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ApiResponse.error(errorMsg, 400);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handlerConstraintViolation(ConstraintViolationException ex) {
        String errorMsg = ex.getConstraintViolations()
                .iterator()
                .next()
                .getMessage();
        return ApiResponse.error(errorMsg, 400);
    }


    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        return ApiResponse.error(e.getMessage());
    }
}
