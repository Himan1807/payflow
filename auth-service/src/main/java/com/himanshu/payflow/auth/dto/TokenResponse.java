package com.himanshu.payflow.auth.dto;

import com.himanshu.payflow.auth.entity.RefreshToken;
import lombok.*;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType
){
}