package com.example.px_service.common;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseMessageSourceInitializer {

    public ApiResponseMessageSourceInitializer(MessageSource messageSource) {
        ApiResponse.setMessageSource(messageSource);
    }
}
