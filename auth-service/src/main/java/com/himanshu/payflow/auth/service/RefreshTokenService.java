package com.himanshu.payflow.auth.service;

import com.himanshu.payflow.auth.entity.AppUser;
import com.himanshu.payflow.auth.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {
    RefreshToken createRefreshToken(AppUser user);

    RefreshToken verifyRefreshToken(String token);

    void deleteByUser(AppUser user);
}