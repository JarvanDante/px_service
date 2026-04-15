package com.example.px_service.controller.frontend;

import com.example.px_service.common.context.UserContext;
import com.example.px_service.common.response.ApiResponse;
import com.example.px_service.common.routes.ApiRoutes;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(ApiRoutes.USER_PROFILE)
    public ApiResponse<Map> profile(HttpServletRequest request) {
        Long userId = UserContext.getUserId();
        String username = UserContext.getUsername();
        Long siteId = UserContext.getSiteId();
        Long channelId = UserContext.getChannelId();
        Map map = new HashMap();
        map.put("user_id", userId);
        map.put("site_id", siteId);
        map.put("username", username);
        map.put("channel_id", channelId);

        return ApiResponse.success(map);
    }

    @GetMapping(ApiRoutes.USER_LIST)
    public ApiResponse<List<UserResponse>> listUsers() {
        return ApiResponse.success(authService.listUsers());
    }

}
