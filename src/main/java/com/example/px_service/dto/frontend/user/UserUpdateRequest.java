package com.example.px_service.dto.frontend.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UserUpdateRequest {


    @NotBlank(message = "{user.password.notnull}")
    @Size(min = 6, max = 16, message = "{user.password.size}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "{user.password.pattern}"
    )
    private String password;
    private String mobile;


    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }


}
