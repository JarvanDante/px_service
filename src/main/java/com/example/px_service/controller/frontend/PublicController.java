package com.example.px_service.controller.frontend;

import com.example.px_service.common.ApiResponse;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.service.PublicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/frontend")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @PostMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> listUsers() {
        return ApiResponse.success(publicService.listUsers());
//        return ApiResponse.error("出错误了！");
    }

}
