package com.example.px_service.dto;

import com.example.px_service.domain.User;

public record UserResponse(
        long id,
        Integer siteId,
        Integer channelId,
        String username,
        String registerIp,
        String registerUrl
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getSiteId(),
                user.getChannelId(),
                user.getUsername(),
                user.getRegisterIp(),
                user.getRegisterUrl()
        );
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public Integer siteId() {
        return siteId;
    }

    @Override
    public Integer channelId() {
        return channelId;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String registerIp() {
        return registerIp;
    }

    @Override
    public String registerUrl() {
        return registerUrl;
    }
}
