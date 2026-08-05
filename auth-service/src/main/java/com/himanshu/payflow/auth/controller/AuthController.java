package com.himanshu.payflow.auth.controller;

import com.himanshu.payflow.auth.dto.*;
import com.himanshu.payflow.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody LoginRequest request) {

        System.out.println("LOGIN CONTROLLER HIT");
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserResponse> me() {
        return ResponseEntity.ok
                (authService.getCurrentUser());
    }

    @GetMapping(path = "/admin")
//    Spring automatically prefixes ROLE_ when you use hasRole().
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Welcome Admin");
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }
}
