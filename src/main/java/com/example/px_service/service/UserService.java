package com.example.px_service.service;

import com.example.px_service.common.enums.BizCode;
import com.example.px_service.common.exception.BizException;
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

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return null
     */
    public Void deleteUser(Integer id) {

        User user = userMapper.findById(id);
        if (user == null) {
            throw new BizException(BizCode.USER_NOT_EXIST);
        }
        userMapper.deleteById(id);
        return null;
    }


}


