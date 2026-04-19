package com.example.px_service.service;

import com.example.px_service.domain.User;
import com.example.px_service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUser(Integer id) {
        return userMapper.findById(id);
    }

}


