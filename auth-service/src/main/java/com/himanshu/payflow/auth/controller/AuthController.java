package com.himanshu.payflow.auth.controller;

import com.himanshu.payflow.auth.dto.LoginRequest;
import com.himanshu.payflow.auth.dto.LoginResponse;
import com.himanshu.payflow.auth.dto.RegisterRequest;
import com.himanshu.payflow.auth.dto.RegisterResponse;
import com.himanshu.payflow.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        System.out.println("LOGIN CONTROLLER HIT");
        return ResponseEntity.ok(authService.login(request));
    }
}
