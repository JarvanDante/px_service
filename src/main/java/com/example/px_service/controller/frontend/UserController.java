package com.example.px_service.controller.frontend;

import com.example.px_service.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/frontend")
public class UserController {

    @GetMapping("/me")
    public ApiResponse<Long> me(HttpServletRequest request, Locale locale) {
        Long userId = (Long) request.getAttribute("userId");

        return ApiResponse.success(userId, locale);
    }

}
