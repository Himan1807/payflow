package com.himanshu.payflow.auth.mapper;

import com.himanshu.payflow.auth.dto.RegisterRequest;
import com.himanshu.payflow.auth.dto.RegisterResponse;
import com.himanshu.payflow.auth.dto.UserResponse;
import com.himanshu.payflow.auth.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUser toEntity(RegisterRequest request) {

        AppUser user = new AppUser();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());

        return user;
    }

    public RegisterResponse toRegisterResponse(AppUser user) {

        RegisterResponse response = new RegisterResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());

        return response;
    }

    public UserResponse toUserResponse(AppUser user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
