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


    /**
     * 更新用户信息
     *
     * @param id   用户ID
     * @param user 包含更新信息的用户对象
     * @return 更新后的用户对象
     */
    public User updateUser(Integer id, User user) {
        // 检查用户是否存在
        User oldUser = userMapper.findById(id);
        if (oldUser == null) {
            throw new BizException(BizCode.USER_NOT_EXIST);
        }
        oldUser.setPassword(user.getPassword());
        oldUser.setMobile(user.getMobile());

        // 执行更新操作并验证结果
        int updateCount = userMapper.update(oldUser);
        if (updateCount <= 0) {
            throw new BizException(BizCode.USER_UPDATE_FAILED);
        }

        return userMapper.findById(oldUser.getId());
    }


}


