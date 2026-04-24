package com.example.px_service.controller.frontend;

import com.example.px_service.common.context.UserContext;
import com.example.px_service.common.response.ApiResponse;
import com.example.px_service.common.routes.ApiRoutes;
import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.user.UserListRequest;
import com.example.px_service.dto.frontend.user.UserUpdateRequest;
import com.example.px_service.service.AuthService;
import com.example.px_service.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Tag(name = "用户管理", description = "用户管理相关的接口")
    @Operation(summary = "用户列表", description = "获取用户列表")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功"),
    })
    @GetMapping(ApiRoutes.USER_LIST)
    public ApiResponse<List<UserResponse>> listUsers(@Valid @ModelAttribute UserListRequest dto) {
        return ApiResponse.success(authService.listUsers(dto));
    }

    @Tag(name = "用户管理", description = "用户管理相关的接口")
    @Operation(summary = "当前用户", description = "获取当前用户信息")
    @GetMapping(ApiRoutes.USER_ONE)
    public ApiResponse<UserResponse> getUser(@Parameter(description = "用户的ID", example = "1000361") @PathVariable Integer id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ApiResponse.error("User not found", 404);
        }
        return ApiResponse.success(UserResponse.from(user));
    }

    @DeleteMapping(ApiRoutes.USER_DELETE)
    public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }


    /**
     * 更新用户信息
     *
     * @param id            用户ID
     * @param userUpdateDto 包含更新信息的用户DTO对象
     * @return 更新后的用户信息响应
     */
    @PutMapping(ApiRoutes.USER_UPDATE)
    public ApiResponse<UserResponse> updateUser(@PathVariable Integer id, @Valid @RequestBody @NonNull UserUpdateRequest userUpdateDto) {
        System.out.println(id);
        // 构建用户对象并加密密码
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        newUser.setMobile(userUpdateDto.getMobile());

        // 调用服务层更新用户
        User updatedUser = userService.updateUser(id, newUser);
        if (updatedUser == null) {
            return ApiResponse.error("User not found", 404);
        }
        return ApiResponse.success(UserResponse.from(updatedUser));
    }


}
