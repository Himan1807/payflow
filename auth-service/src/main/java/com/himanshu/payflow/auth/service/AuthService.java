package com.himanshu.payflow.auth.service;

import com.himanshu.payflow.auth.dto.LoginRequest;
import com.himanshu.payflow.auth.dto.LoginResponse;
import com.himanshu.payflow.auth.dto.RegisterRequest;
import com.himanshu.payflow.auth.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
