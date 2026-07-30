package com.himanshu.payflow.auth.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final AppUserMapper mapper;

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
}
