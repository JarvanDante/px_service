package com.example.px_service.controller.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class TestController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test-msg")
    public String test(Locale locale) {  // Spring 自动注入
        String msg = messageSource.getMessage("success", null, locale);
        return "locale=" + locale + ", message=" + msg;
    }
}
