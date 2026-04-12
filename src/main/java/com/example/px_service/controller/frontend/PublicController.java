package com.example.px_service.controller.frontend;

import com.example.px_service.common.ApiResponse;
import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.Public.LoginRequest;
import com.example.px_service.dto.frontend.Public.RegisterRequest;
import com.example.px_service.service.PublicService;
import com.example.px_service.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frontend")
@Validated  // 方法级别的参数校验必须加在类上
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> listUsers() {
        return ApiResponse.success(publicService.listUsers());
//        return ApiResponse.error("出错误了！");
    }

    /**
     * 登录接口
     *
     * @param dto
     * @return
     */
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

    /**
     * 注册接口
     *
     * @param regDto
     * @return
     */
    @PostMapping("/register")
    public ApiResponse<User> register(@Valid @RequestBody RegisterRequest regDto) {

        return ApiResponse.success(publicService.createUser(regDto));
    }

}
