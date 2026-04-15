package com.example.px_service.common.exception;

import com.example.px_service.common.enums.ResultCode;
import com.example.px_service.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(BizException.class)
    public ApiResponse<?> handleBizException(BizException ex) {
        return ApiResponse.errorByKey(ex.getMessageKey(), ex.getCode());
    }


    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("Unhandled exception", e);
        return ApiResponse.errorByKey(ResultCode.ERROR.getMessage(), ResultCode.ERROR.getCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse handleBodyMissing() {
        return ApiResponse.errorByKey("request.body.missing", 400);
    }
}
