package com.example.px_service.dto.frontend.Public;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 16, message = "用户名长度必须在5-16之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "用户名长度必须在5-16之间")
//    @Min(value = 7, message = "密码必须大于等于7")
//    @Max(value = 16, message = "密码必须小于等于16")
    private String password;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
