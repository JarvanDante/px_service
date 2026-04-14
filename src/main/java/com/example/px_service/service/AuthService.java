package com.example.px_service.service;

import com.example.px_service.common.BizException;
import com.example.px_service.common.ErrorCode;
import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
import com.example.px_service.dto.frontend.Auth.RegisterRequest;
import com.example.px_service.repository.UserRepository;
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

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User loginUser(@RequestBody LoginRequest loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        //用户名唯一
        if (!userRepository.existsByUsername(username)) {
            throw new BizException(ErrorCode.USERNAME_DUPLICATE);
        }
        //密码判断
        User user = userRepository.findByUsername(username);
        Boolean isMatch = passwordEncoder.matches(password, user.getPassword());
        if (!isMatch) {
            throw new BizException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        return user;
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
            throw new BizException(ErrorCode.USERNAME_DUPLICATE);
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
