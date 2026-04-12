package com.example.px_service.controller;

import com.example.px_service.common.ApiResponse;
import com.example.px_service.dto.LoginRequest;
import com.example.px_service.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/frontend")
@Validated  // 方法级别的参数校验必须加在类上

public class AuthController {

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        if (!"admin".equals(username) || !"123456".equals(password)) {
            return ApiResponse.error("用户名或密码错误");
        }

        //假设 用户id=1
        String token = JwtUtil.generateToken(1);


        return ApiResponse.success(token);
    }
}
