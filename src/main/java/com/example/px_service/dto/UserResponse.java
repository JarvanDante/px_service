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
}
