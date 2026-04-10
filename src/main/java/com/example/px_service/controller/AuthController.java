package com.example.px_service.controller;

import com.example.px_service.common.ApiResponse;
import com.example.px_service.dto.LoginRequest;
import com.example.px_service.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frontend")
public class AuthController {

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (!"admin".equals(username) || !"123456".equals(password)) {
            return ApiResponse.error("用户名或密码错误");
        }

        //假设 用户id=1
        String token = JwtUtil.generateToken(1);

        return ApiResponse.success(token);
    }
}
