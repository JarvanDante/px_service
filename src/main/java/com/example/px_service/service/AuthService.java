package com.example.px_service.service;

import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
import com.example.px_service.dto.frontend.Auth.LoginResponse;
import com.example.px_service.dto.frontend.Auth.RegisterRequest;
import com.example.px_service.dto.frontend.user.UserListRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface AuthService {

    // 用户列表
    List<UserResponse> listUsers(@ModelAttribute UserListRequest dto);

    // 登录
    LoginResponse loginUser(@RequestBody LoginRequest loginDto);

    // 注册/新增用户
    User createUser(@RequestBody RegisterRequest regDto);


}
