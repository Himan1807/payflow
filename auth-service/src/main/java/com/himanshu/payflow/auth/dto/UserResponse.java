package com.himanshu.payflow.auth.dto;

import com.himanshu.payflow.auth.entity.Role;
import lombok.Builder;

@Builder
public record UserResponse (
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        Role role
){
}
