package com.himanshu.payflow.auth.exception;

public class RefreshTokenExpiredException  extends RuntimeException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
