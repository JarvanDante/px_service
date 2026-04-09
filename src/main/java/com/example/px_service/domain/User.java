package com.example.px_service.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer siteId;

    private Integer gradeId;

    private Integer levelId;

    private Integer agentId;

    private Integer channelId;

    private String username;

    private String password;

    private String payPassword;
    private Integer status;
    private String registerIp;
    private Instant registerTime;
    @Column(columnDefinition = "TEXT")
    private String registerUrl;
    private Integer registerDevice;
    private String deviceCode;
    private String lastLoginIp;
    private Instant lastLoginTime;
    private String lastLoginAddress;
    @Column(length = 50)
    private String realname;
    @Column(length = 20)
    private String mobile;
    private String email;
    private String qq;
    private LocalDate birthday;
    private Integer sex;
    private Integer isOnline;
    private Integer focusLevel;
    private Integer balanceStatus;
    @Column(length = 100)
    private String safeQuestion;
    @Column(length = 100)
    private String safeAnswer;
    private Integer showBeginnerGuide;
    private String remark;
    private String siteCode;
    private String channelCode;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer payTimes;
    private Integer hasPass;
    private String currency;
    private Integer withdrawTimes;
    private Integer deleteAt;
    //=====set and get

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

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Instant getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Instant registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public Integer getRegisterDevice() {
        return registerDevice;
    }

    public void setRegisterDevice(Integer registerDevice) {
        this.registerDevice = registerDevice;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
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

    public String getLastLoginAddress() {
        return lastLoginAddress;
    }

    public void setLastLoginAddress(String lastLoginAddress) {
        this.lastLoginAddress = lastLoginAddress;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getFocusLevel() {
        return focusLevel;
    }

    public void setFocusLevel(Integer focusLevel) {
        this.focusLevel = focusLevel;
    }

    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getSafeQuestion() {
        return safeQuestion;
    }

    public void setSafeQuestion(String safeQuestion) {
        this.safeQuestion = safeQuestion;
    }

    public String getSafeAnswer() {
        return safeAnswer;
    }

    public void setSafeAnswer(String safeAnswer) {
        this.safeAnswer = safeAnswer;
    }

    public Integer getShowBeginnerGuide() {
        return showBeginnerGuide;
    }

    public void setShowBeginnerGuide(Integer showBeginnerGuide) {
        this.showBeginnerGuide = showBeginnerGuide;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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

    public Integer getPayTimes() {
        return payTimes;
    }

    public void setPayTimes(Integer payTimes) {
        this.payTimes = payTimes;
    }

    public Integer getHasPass() {
        return hasPass;
    }

    public void setHasPass(Integer hasPass) {
        this.hasPass = hasPass;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(Integer withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }

    public Integer getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Integer deleteAt) {
        this.deleteAt = deleteAt;
    }
}
