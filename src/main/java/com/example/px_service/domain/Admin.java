package com.example.px_service.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Admin {
    //主键自增
    private Integer id;

    private Integer siteId = 1;
    private String username;
    private String nickname;
    private String password;
    private Integer adminRoleId;

    private Integer status;
    private Integer switchGoogle2f;
    private String google2faSecret;
    private String lastLoginIp;
    private Instant lastLoginTime;
    private Integer deleteAt;
    private Instant createdAt;
    private Instant updatedAt;

}
