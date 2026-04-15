package com.example.px_service.service;

import com.example.px_service.common.code.BizCode;
import com.example.px_service.common.exception.BizException;
import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
import com.example.px_service.dto.frontend.Auth.LoginResponse;
import com.example.px_service.dto.frontend.Auth.RegisterRequest;
import com.example.px_service.repository.UserRepository;
import com.example.px_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 用户列表
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers() {
        PageRequest page = PageRequest.of(0, 5, Sort.by("id").descending());

        List userList = userRepository.findAll(page)
                .stream()
                .map(UserResponse::from)
                .toList();

        return userList;
    }

    @Transactional
    public LoginResponse loginUser(@RequestBody LoginRequest loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        //用户名唯一
        if (!userRepository.existsByUsername(username)) {
            throw new BizException(BizCode.USERNAME_NOT_EXIST);
        }
        //密码判断
        User user = userRepository.findByUsername(username);
        Boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isMatch) {
            throw new BizException(BizCode.AUTH_INVALID_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(user.getId());

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
        //用户名唯一
        if (userRepository.existsByUsername(regDto.getUsername())) {
            throw new BizException(BizCode.USERNAME_DUPLICATE);
        }

        //插入用户
        User newUser = new User();
        newUser.setUsername(regDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(regDto.getPassword()));
        if (mobile != null) {
            newUser.setMobile(mobile);
        }

        User savedUser = userRepository.save(newUser);

        return savedUser;
    }


}
