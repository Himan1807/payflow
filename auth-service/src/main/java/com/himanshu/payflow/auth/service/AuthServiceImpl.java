package com.himanshu.payflow.auth.service;

import com.himanshu.payflow.auth.dto.*;
import com.himanshu.payflow.auth.entity.AppUser;
import com.himanshu.payflow.auth.entity.RefreshToken;
import com.himanshu.payflow.auth.entity.Role;
import com.himanshu.payflow.auth.exception.EmailAlreadyExistsException;
import com.himanshu.payflow.auth.exception.PhoneNumberAlreadyExistsException;
import com.himanshu.payflow.auth.mapper.AppUserMapper;
import com.himanshu.payflow.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final AppUserMapper mapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        AppUser user = mapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);

        AppUser savedUser = repository.save(user);

        return mapper.toRegisterResponse(savedUser);
    }

    @Override
    public TokenResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        AppUser user = (AppUser) authentication.getPrincipal();

        assert user != null;
        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    public UserResponse getCurrentUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        AppUser user = (AppUser) authentication.getPrincipal();
        return mapper.toUserResponse(user);
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService
                .verifyRefreshToken(
                        request.refreshToken()
                );

        AppUser user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    public void logout(LogoutRequest request) {
        RefreshToken refreshToken = refreshTokenService
                .verifyRefreshToken(
                        request.refreshToken()
                );

        refreshTokenService.deleteByUser(
                refreshToken.getUser()
        );
    }
}
