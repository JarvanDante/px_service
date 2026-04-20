package com.example.px_service;

import com.example.px_service.domain.User;
import com.example.px_service.mapper.UserMapper;
import com.example.px_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    void getUser_shouldReturnUser_whenUserExists() {
        // given
        Integer id = 1;
        User expectedUser = new User();
        expectedUser.setId(id);
        when(userMapper.findById(id)).thenReturn(expectedUser);

        System.out.println(expectedUser.getId());
        System.out.println(id);
        // when
        User actualUser = userService.getUser(id);
        System.out.println(actualUser.getId());
        // then
        assertThat(actualUser).isEqualTo(expectedUser);
    }
}
