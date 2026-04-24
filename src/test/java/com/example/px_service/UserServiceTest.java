package com.example.px_service;

import com.example.px_service.domain.User;
import com.example.px_service.mapper.UserMapper;
import com.example.px_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Test
    void getUser_shouldReturnUser_whenUserExists() {
        Integer id = 1;
        User expectedUser = new User();
        expectedUser.setId(id);

        when(userMapper.selectById(id)).thenReturn(expectedUser);

        User actualUser = userService.getUser(id);

        assertThat(actualUser).isEqualTo(expectedUser);
        verify(userMapper).selectById(id);
    }
}
