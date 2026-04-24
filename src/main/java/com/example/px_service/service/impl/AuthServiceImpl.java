package com.example.px_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.px_service.common.enums.BizCode;
import com.example.px_service.common.exception.BizException;
import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
import com.example.px_service.dto.frontend.Auth.LoginResponse;
import com.example.px_service.dto.frontend.Auth.RegisterRequest;
import com.example.px_service.dto.frontend.user.UserListRequest;
import com.example.px_service.mapper.UserMapper;
import com.example.px_service.service.AuthService;
import com.example.px_service.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 用户列表
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers(@ModelAttribute UserListRequest dto) {
        //接收参数 page 和 size
        Integer page = dto.getPage();
        Integer size = dto.getSize();
        int safePage = page == null ? 1 : Math.max(page, 0);
        int safeSize = size == null ? 10 : Math.max(size, 1);
        int offset = safePage * safeSize;

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.select(User::getId, User::getSiteId, User::getChannelId, User::getUsername, User::getRegisterIp, User::getRegisterUrl);
        lambdaQueryWrapper.last("LIMIT " + offset + "," + safeSize);

        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        List<UserResponse> userResponseList = userList.stream().map(UserResponse::from).toList();
        return userResponseList;
    }

    @Transactional
    public LoginResponse loginUser(@RequestBody LoginRequest loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);


        //用户名唯一
        if (user == null) {
            throw new BizException(BizCode.USERNAME_NOT_EXIST);
        }
        //密码判断
//        User user = userMapper.findByUsername(username);
        Boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isMatch) {
            throw new BizException(BizCode.AUTH_INVALID_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(user, "app");

        LoginResponse.UserInfo userInfo = LoginResponse.UserInfo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
        LoginResponse loginResponse = LoginResponse
                .builder()
//                .tokenType("Bearer")
                .token(token)
                .expiresIn(3600L)
                .userInfo(userInfo)
                .build();
        return loginResponse;
    }

    /**
     * 注册/新增用户
     *
     * @param regDto
     * @return
     */
    @Transactional
    public User createUser(@RequestBody RegisterRequest regDto) {
        String mobile = regDto.getMobile();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", regDto.getUsername());
        // 用户名唯一
        User existingUser = userMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            throw new BizException(BizCode.USERNAME_DUPLICATE);
        }

        //插入用户
        User newUser = new User();
        newUser.setUsername(regDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(regDto.getPassword()));
        if (mobile != null) {
            newUser.setMobile(mobile);
        }

        userMapper.insert(newUser);
        return newUser;
    }

}
