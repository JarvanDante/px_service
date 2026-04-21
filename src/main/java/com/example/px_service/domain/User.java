package com.example.px_service.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
public class User {
    private Integer id;

    private Integer siteId = 1;

    private Integer gradeId = 1;

    private Integer levelId = 0;

    private Integer agentId = 0;

    private Integer channelId = 0;

    private String username;

    private String password;

    private String payPassword;
    private Integer status;
    private String registerIp;
    private Instant registerTime;
    private String registerUrl;
    private Integer registerDevice;
    private String deviceCode;
    private String lastLoginIp;
    private Instant lastLoginTime;
    private String lastLoginAddress;
    private String realname;
    private String mobile;
    private String email;
    private String qq;
    private LocalDate birthday;
    private Integer sex;
    private Integer isOnline;
    private Integer focusLevel;
    private Integer balanceStatus;
    private String safeQuestion;
    private String safeAnswer;
    private Integer showBeginnerGuide;
    private String remark = "";
    private String siteCode;
    private String channelCode;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer payTimes = 0;
    private Integer hasPass;
    private String currency;
    private Integer withdrawTimes = 0;
    private Integer deleteAt;

}
