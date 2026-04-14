package com.example.px_service.service;

import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.Auth.RegisterRequest;
import com.example.px_service.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User createUser(@RequestBody RegisterRequest regDto) {
        String mobile = regDto.getMobile();

        User newUser = new User();
        newUser.setUsername(regDto.getUsername());
        newUser.setPassword(regDto.getPassword());
        if (mobile != null) {
            newUser.setMobile(mobile);
        }

        User savedUser = userRepository.save(newUser);

        return savedUser;
    }


}
