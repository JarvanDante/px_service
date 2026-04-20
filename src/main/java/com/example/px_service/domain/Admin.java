package com.example.px_service.domain;

import java.time.Instant;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdminRoleId() {
        return adminRoleId;
    }

    public void setAdminRoleId(Integer adminRoleId) {
        this.adminRoleId = adminRoleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSwitchGoogle2f() {
        return switchGoogle2f;
    }

    public void setSwitchGoogle2f(Integer switchGoogle2f) {
        this.switchGoogle2f = switchGoogle2f;
    }

    public String getGoogle2faSecret() {
        return google2faSecret;
    }

    public void setGoogle2faSecret(String google2faSecret) {
        this.google2faSecret = google2faSecret;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Instant getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Instant lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Integer deleteAt) {
        this.deleteAt = deleteAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Instant createdAt;
    private Instant updatedAt;

}
