package com.example.px_service;

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
import com.example.px_service.service.impl.AuthServiceImpl;
import com.example.px_service.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void createUser_shouldThrowException_whenUsernameAlreadyExists() {
        RegisterRequest request = mockRegisterRequest("existinguser", "password123", "13800138000");
        when(userMapper.selectOne(any(QueryWrapper.class))).thenReturn(createUserEntity("existinguser", "encoded"));

        assertThatThrownBy(() -> authService.createUser(request))
                .isInstanceOfSatisfying(BizException.class, ex ->
                        assertThat(ex.getErrorCode()).isEqualTo(BizCode.USERNAME_DUPLICATE));

        verify(userMapper).selectOne(any(QueryWrapper.class));
        verifyNoMoreInteractions(userMapper);
        verifyNoInteractions(passwordEncoder, jwtUtil);
    }

    @Test
    void createUser_shouldCreateUserSuccessfully_whenMobileIsProvided() {
        String username = "newuser";
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedPasswordHash";
        String mobile = "13800138000";
        Integer userId = 100;

        RegisterRequest request = mockRegisterRequest(username, rawPassword, mobile);
        when(userMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userMapper.insert(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(userId);
            return 1;
        });

        User result = authService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getMobile()).isEqualTo(mobile);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).selectOne(any(QueryWrapper.class));
        verify(userMapper).insert(userCaptor.capture());
        verify(passwordEncoder).encode(rawPassword);

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUsername()).isEqualTo(username);
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(capturedUser.getMobile()).isEqualTo(mobile);
    }

    @Test
    void createUser_shouldCreateUserSuccessfully_whenMobileIsNull() {
        String username = "newuser2";
        String rawPassword = "password456";
        String encodedPassword = "$2a$10$encodedPasswordHash2";
        Integer userId = 101;

        RegisterRequest request = mockRegisterRequest(username, rawPassword, null);
        when(userMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userMapper.insert(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(userId);
            return 1;
        });

        User result = authService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getMobile()).isNull();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).selectOne(any(QueryWrapper.class));
        verify(userMapper).insert(userCaptor.capture());
        verify(passwordEncoder).encode(rawPassword);

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUsername()).isEqualTo(username);
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(capturedUser.getMobile()).isNull();
    }

    @Test
    void listUsers_shouldReturnEmptyList_whenNoUsersExist() {
        UserListRequest request = new UserListRequest();
        request.setPage(1);
        request.setSize(10);

        when(userMapper.selectList(any(QueryWrapper.class))).thenReturn(List.of());

        List<UserResponse> result = authService.listUsers(request);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(userMapper).selectList(any(QueryWrapper.class));
    }

    @Test
    void listUsers_shouldReturnPagedUsers_whenUsersExist() {
        UserListRequest request = new UserListRequest();
        request.setPage(0);
        request.setSize(2);

        User user1 = createUserEntity("user1", "password1");
        user1.setId(1);
        user1.setRegisterIp("127.0.0.1");
        user1.setRegisterUrl("/signup");

        User user2 = createUserEntity("user2", "password2");
        user2.setId(2);
        user2.setRegisterIp("127.0.0.2");
        user2.setRegisterUrl("/register");

        when(userMapper.selectList(any(QueryWrapper.class))).thenReturn(List.of(user1, user2));

        List<UserResponse> result = authService.listUsers(request);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(user1.getId());
        assertThat(result.get(0).username()).isEqualTo("user1");
        assertThat(result.get(0).siteId()).isEqualTo(user1.getSiteId());
        assertThat(result.get(0).channelId()).isEqualTo(user1.getChannelId());
        assertThat(result.get(0).registerIp()).isEqualTo(user1.getRegisterIp());
        assertThat(result.get(0).registerUrl()).isEqualTo(user1.getRegisterUrl());

        assertThat(result.get(1).id()).isEqualTo(user2.getId());
        assertThat(result.get(1).username()).isEqualTo("user2");
        assertThat(result.get(1).siteId()).isEqualTo(user2.getSiteId());
        assertThat(result.get(1).channelId()).isEqualTo(user2.getChannelId());
        assertThat(result.get(1).registerIp()).isEqualTo(user2.getRegisterIp());
        assertThat(result.get(1).registerUrl()).isEqualTo(user2.getRegisterUrl());

        verify(userMapper).selectList(any(QueryWrapper.class));
    }

    @Test
    void listUsers_shouldUseDefaultPageParameters_whenNotProvided() {
        UserListRequest request = new UserListRequest();

        when(userMapper.selectList(any(QueryWrapper.class))).thenReturn(List.of());

        List<UserResponse> result = authService.listUsers(request);

        assertThat(result).isEmpty();
        verify(userMapper).selectList(any(QueryWrapper.class));
    }

    @Test
    void loginUser_shouldThrowException_whenUserDoesNotExist() {
        LoginRequest request = new LoginRequest();
        request.setUsername("test");
        request.setPassword("password");

        when(userMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        assertThatThrownBy(() -> authService.loginUser(request))
                .isInstanceOfSatisfying(BizException.class, ex ->
                        assertThat(ex.getErrorCode()).isEqualTo(BizCode.USERNAME_NOT_EXIST));

        verify(userMapper).selectOne(any(QueryWrapper.class));
        verifyNoInteractions(passwordEncoder, jwtUtil);
    }

    @Test
    void loginUser_shouldThrowException_whenPasswordIsIncorrect() {
        String username = "testuser";
        String rawPassword = "wrongpassword";
        String encodedPassword = "$2a$10$encodedPasswordHash";

        User user = createUserEntity(username, encodedPassword);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(rawPassword);

        when(userMapper.selectOne(any(QueryWrapper.class))).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        assertThatThrownBy(() -> authService.loginUser(request))
                .isInstanceOfSatisfying(BizException.class, ex ->
                        assertThat(ex.getErrorCode()).isEqualTo(BizCode.AUTH_INVALID_CREDENTIALS));

        verify(userMapper).selectOne(any(QueryWrapper.class));
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void loginUser_shouldReturnLoginResponse_whenCredentialsAreValid() {
        String username = "testuser";
        String rawPassword = "correctpassword";
        String encodedPassword = "$2a$10$encodedPasswordHash";
        String generatedToken = "mocked.jwt.token";
        Integer userId = 1;
        String email = "test@example.com";

        User user = createUserEntity(username, encodedPassword);
        user.setId(userId);
        user.setEmail(email);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(rawPassword);

        when(userMapper.selectOne(any(QueryWrapper.class))).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(user, "app")).thenReturn(generatedToken);

        LoginResponse response = authService.loginUser(request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(generatedToken);
        assertThat(response.getExpiresIn()).isEqualTo(3600L);
        assertThat(response.getUserInfo()).isNotNull();
        assertThat(response.getUserInfo().getId()).isEqualTo(userId);
        assertThat(response.getUserInfo().getUsername()).isEqualTo(username);
        assertThat(response.getUserInfo().getEmail()).isEqualTo(email);

        verify(userMapper).selectOne(any(QueryWrapper.class));
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtUtil).generateToken(user, "app");
    }

    private RegisterRequest mockRegisterRequest(String username, String password, String mobile) {
        RegisterRequest request = org.mockito.Mockito.mock(RegisterRequest.class);
        when(request.getUsername()).thenReturn(username);
        when(request.getPassword()).thenReturn(password);
        when(request.getMobile()).thenReturn(mobile);
        return request;
    }

    private User createUserEntity(String username, String password) {
        User user = new User();
        user.setId(1);
        user.setUsername(username);
        user.setPassword(password);
        user.setSiteId(1);
        user.setChannelId(0);
        user.setEmail("test@example.com");
        user.setRegisterTime(Instant.now());
        return user;
    }
}
