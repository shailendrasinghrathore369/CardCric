package com.cardcric.application.port.input;

import com.cardcric.application.dto.AuthResponse;
import com.cardcric.application.dto.LoginRequest;
import com.cardcric.application.dto.RefreshTokenRequest;
import com.cardcric.application.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
    void logout(String refreshToken);
}
