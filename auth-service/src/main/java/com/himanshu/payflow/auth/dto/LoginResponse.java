package com.himanshu.payflow.auth.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String token, String tokenType, long expiresIn){

}