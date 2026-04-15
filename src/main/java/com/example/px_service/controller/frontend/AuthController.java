package com.example.px_service.controller.frontend;

import com.example.px_service.common.response.ApiResponse;
import com.example.px_service.common.routes.ApiRoutes;
import com.example.px_service.domain.User;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
import com.example.px_service.dto.frontend.Auth.LoginResponse;
import com.example.px_service.dto.frontend.Auth.RegisterRequest;
import com.example.px_service.service.AuthService;
import com.example.px_service.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthService authService;


    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
    }

    @PostMapping(ApiRoutes.AUTH_LOGIN)
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest dto) {
        LoginResponse loginResponse = authService.loginUser(dto);

        return ApiResponse.success(loginResponse);
    }

    @PostMapping(ApiRoutes.AUTH_REGISTER)
    public ApiResponse<User> register(@Valid @RequestBody RegisterRequest regDto) {
        return ApiResponse.success(authService.createUser(regDto));
    }
}
