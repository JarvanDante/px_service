package com.example.px_service;

import com.example.px_service.common.enums.BizCode;
import com.example.px_service.common.exception.BizException;
import com.example.px_service.domain.User;
import com.example.px_service.dto.UserResponse;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
import com.example.px_service.dto.frontend.Auth.LoginResponse;
import com.example.px_service.dto.frontend.Auth.RegisterRequest;
import com.example.px_service.dto.frontend.user.UserListRequest;
import com.example.px_service.repository.UserRepository;
import com.example.px_service.service.AuthService;
import com.example.px_service.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void createUser_shouldThrowException_whenUsernameAlreadyExists() {
        RegisterRequest request = mock(RegisterRequest.class);
        when(request.getUsername()).thenReturn("existinguser");
        when(request.getMobile()).thenReturn("13800138000");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        assertThatThrownBy(() -> authService.createUser(request))
                .isInstanceOfSatisfying(BizException.class, ex -> {
                    assertThat(ex.getErrorCode()).isEqualTo(BizCode.USERNAME_DUPLICATE);
                });

        verify(userRepository).existsByUsername("existinguser");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void createUser_shouldCreateUserSuccessfully_whenMobileIsProvided() {
        String username = "newuser";
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedPasswordHash";
        String mobile = "13800138000";
        Long userId = 100L;

        RegisterRequest request = mock(RegisterRequest.class);
        when(request.getUsername()).thenReturn(username);
        when(request.getPassword()).thenReturn(rawPassword);
        when(request.getMobile()).thenReturn(mobile);

        User savedUser = new User();
        savedUser.setId(userId.intValue());
        savedUser.setUsername(username);
        savedUser.setPassword(encodedPassword);
        savedUser.setMobile(mobile);

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = authService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getMobile()).isEqualTo(mobile);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUsername()).isEqualTo(username);
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(capturedUser.getMobile()).isEqualTo(mobile);

        verify(userRepository).existsByUsername(username);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void createUser_shouldCreateUserSuccessfully_whenMobileIsNull() {
        String username = "newuser2";
        String rawPassword = "password456";
        String encodedPassword = "$2a$10$encodedPasswordHash2";
        Long userId = 101L;

        RegisterRequest request = mock(RegisterRequest.class);
        when(request.getUsername()).thenReturn(username);
        when(request.getPassword()).thenReturn(rawPassword);
        when(request.getMobile()).thenReturn(null);

        User savedUser = new User();
        savedUser.setId(userId.intValue());
        savedUser.setUsername(username);
        savedUser.setPassword(encodedPassword);

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = authService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getMobile()).isNull();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUsername()).isEqualTo(username);
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(capturedUser.getMobile()).isNull();

        verify(userRepository).existsByUsername(username);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void listUsers_shouldReturnEmptyList_whenNoUsersExist() {
        UserListRequest request = new UserListRequest();
        request.setPage(1);
        request.setSize(10);

        Page<User> emptyPage = new PageImpl<>(Collections.emptyList());
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        List<UserResponse> result = authService.listUsers(request);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(userRepository).findAll(any(PageRequest.class));
    }

    @Test
    void listUsers_shouldReturnPagedUsers_whenUsersExist() {
        UserListRequest request = new UserListRequest();
        request.setPage(0);
        request.setSize(2);

        User user1 = createUser("user1", "password1");
        user1.setId(1);
        user1.setEmail("user1@example.com");

        User user2 = createUser("user2", "password2");
        user2.setId(2);
        user2.setEmail("user2@example.com");

        List<User> userList = List.of(user1, user2);
        Page<User> userPage = new PageImpl<>(userList);

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(userPage);

        List<UserResponse> result = authService.listUsers(request);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(user1.getId());
        assertThat(result.get(0).username()).isEqualTo("user1");
        assertThat(result.get(0).siteId()).isEqualTo(user1.getSiteId());
        assertThat(result.get(0).channelId()).isEqualTo(user1.getChannelId());
        assertThat(result.get(1).id()).isEqualTo(user2.getId());
        assertThat(result.get(1).username()).isEqualTo("user2");
        assertThat(result.get(1).siteId()).isEqualTo(user2.getSiteId());
        assertThat(result.get(1).channelId()).isEqualTo(user2.getChannelId());

        verify(userRepository).findAll(any(PageRequest.class));
    }

    @Test
    void listUsers_shouldUseDefaultPageParameters_whenNotProvided() {
        UserListRequest request = new UserListRequest();

        Page<User> emptyPage = new PageImpl<>(Collections.emptyList());
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        authService.listUsers(request);

        verify(userRepository).findAll(argThat((Pageable pageRequest) ->
                pageRequest.getPageNumber() == 1 &&
                        pageRequest.getPageSize() == 10 &&
                        pageRequest.getSort().getOrderFor("id") != null &&
                        pageRequest.getSort().getOrderFor("id").isDescending()
        ));
    }

    @Test
    void loginUser_shouldThrowException_whenUserDoesNotExist() {
        LoginRequest request = new LoginRequest();
        request.setUsername("test");
        request.setPassword("password");

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);

        assertThatThrownBy(() -> authService.loginUser(request))
                .isInstanceOfSatisfying(BizException.class, ex -> {
                    assertThat(ex.getErrorCode()).isEqualTo(BizCode.USERNAME_NOT_EXIST);
                });

        verify(userRepository).existsByUsername(request.getUsername());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder, jwtUtil);
    }

    @Test
    void loginUser_shouldThrowException_whenPasswordIsIncorrect() {
        String username = "testuser";
        String rawPassword = "wrongpassword";
        String encodedPassword = "$2a$10$encodedPasswordHash";

        User user = createUser(username, encodedPassword);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(rawPassword);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        assertThatThrownBy(() -> authService.loginUser(request))
                .isInstanceOfSatisfying(BizException.class, ex -> {
                    assertThat(ex.getErrorCode()).isEqualTo(BizCode.AUTH_INVALID_CREDENTIALS);
                });

        verify(userRepository).existsByUsername(username);
        verify(userRepository).findByUsername(username);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void loginUser_shouldReturnLoginResponse_whenCredentialsAreValid() {
        String username = "testuser";
        String rawPassword = "correctpassword";
        String encodedPassword = "$2a$10$encodedPasswordHash";
        String generatedToken = "mocked.jwt.token";
        Long userId = 1L;
        String email = "test@example.com";

        User user = createUser(username, encodedPassword);
        user.setId(userId.intValue());
        user.setEmail(email);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(rawPassword);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(user);
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

        verify(userRepository).existsByUsername(username);
        verify(userRepository).findByUsername(username);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtUtil).generateToken(user, "app");
    }

    private User createUser(String username, String password) {
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
