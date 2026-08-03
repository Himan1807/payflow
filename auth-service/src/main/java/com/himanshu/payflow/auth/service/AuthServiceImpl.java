package com.himanshu.payflow.auth.service;

import com.himanshu.payflow.auth.dto.LoginRequest;
import com.himanshu.payflow.auth.dto.LoginResponse;
import com.himanshu.payflow.auth.dto.RegisterRequest;
import com.himanshu.payflow.auth.dto.RegisterResponse;
import com.himanshu.payflow.auth.entity.AppUser;
import com.himanshu.payflow.auth.entity.Role;
import com.himanshu.payflow.auth.exception.EmailAlreadyExistsException;
import com.himanshu.payflow.auth.exception.PhoneNumberAlreadyExistsException;
import com.himanshu.payflow.auth.mapper.AppUserMapper;
import com.himanshu.payflow.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        System.out.println(authentication);

        AppUser user = (AppUser) authentication.getPrincipal();

        assert user != null;
        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(3600)
                .build();
    }
}
