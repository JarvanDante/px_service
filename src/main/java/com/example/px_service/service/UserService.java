package com.example.px_service.service;

import com.example.px_service.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //删除用户
    Void deleteUser(Integer id);

    //修改用户
    User updateUser(Integer id, User user);


}


