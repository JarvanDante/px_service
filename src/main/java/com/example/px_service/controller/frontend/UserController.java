package com.example.px_service.controller.frontend;

import com.example.px_service.common.context.UserContext;
import com.example.px_service.common.response.ApiResponse;
import com.example.px_service.common.routes.ApiRoutes;
import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.user.UserListRequest;
import com.example.px_service.service.AuthService;
import com.example.px_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

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
    public ApiResponse<List<UserResponse>> listUsers(@Valid @ModelAttribute UserListRequest dto) {
        return ApiResponse.success(authService.listUsers(dto));
    }

    @GetMapping(ApiRoutes.USER_ONE)
    public User getUser(Integer id) {
        return userService.getUser(id);
    }
}
