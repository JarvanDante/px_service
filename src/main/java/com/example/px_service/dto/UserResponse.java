package com.example.px_service.dto;

import com.example.px_service.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(
        @Schema(description = "用户Id")
        Integer id,
        @Schema(description = "商户id,site_id")
        Integer siteId,
        @Schema(description = "渠道id,channel_id")
        Integer channelId,
        @Schema(description = "用户名")
        String username,
        @Schema(description = "注册ip")
        String registerIp,
        @Schema(description = "注册地址")
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
    public Integer id() {
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
