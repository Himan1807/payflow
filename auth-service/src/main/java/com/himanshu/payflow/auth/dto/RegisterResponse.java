package com.himanshu.payflow.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {
    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;
}
