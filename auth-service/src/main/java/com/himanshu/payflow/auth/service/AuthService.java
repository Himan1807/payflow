package com.himanshu.payflow.auth.service;

import com.himanshu.payflow.auth.dto.*;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    TokenResponse login(LoginRequest request);

    UserResponse getCurrentUser();

    TokenResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);
}