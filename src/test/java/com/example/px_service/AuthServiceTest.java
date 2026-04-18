package com.example.px_service;

import com.example.px_service.common.enums.BizCode;
import com.example.px_service.common.exception.BizException;
import com.example.px_service.dto.frontend.Auth.LoginRequest;
import com.example.px_service.repository.UserRepository;
import com.example.px_service.service.AuthService;
import com.example.px_service.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
}
