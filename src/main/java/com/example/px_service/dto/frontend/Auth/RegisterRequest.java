package com.example.px_service.dto.frontend.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class RegisterRequest {


    //    private Integer siteId;
    @NotBlank(message = "{user.username.notnull}")
    @Size(min = 6, max = 16, message = "{user.username.size}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "{user.username.pattern}"
    )
    private String username;
    @NotBlank(message = "{user.password.notnull}")
    private String password;
    private String mobile;
    private Integer code;
    @NotNull(message = "{user.reg.time.notnull}")
    private Long time;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getCode() {
        return code;
    }

    public Long getTime() {
        return time;
    }
}
