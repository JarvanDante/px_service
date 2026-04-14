package com.example.px_service.controller.frontend;

import com.example.px_service.common.ApiResponse;
import com.example.px_service.common.ApiRoutes;
import com.example.px_service.domain.User;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
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
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(ApiRoutes.AUTH_LOGIN)
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest dto) {
        User user = authService.loginUser(dto);
        String token = jwtUtil.generateToken(user.getId());
        return ApiResponse.success(token);
    }

    @PostMapping(ApiRoutes.AUTH_REGISTER)
    public ApiResponse<User> register(@Valid @RequestBody RegisterRequest regDto) {
        return ApiResponse.success(authService.createUser(regDto));
    }
}
