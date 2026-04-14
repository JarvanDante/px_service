package com.example.px_service.controller.frontend;

import com.example.px_service.common.ApiResponse;
import com.example.px_service.common.ApiRoutes;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(ApiRoutes.USER_ME)
    public ApiResponse<Long> me(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        return ApiResponse.success(userId);
    }

    @GetMapping(ApiRoutes.USER_LIST)
    public ApiResponse<List<UserResponse>> listUsers() {
        return ApiResponse.success(authService.listUsers());
    }

}
