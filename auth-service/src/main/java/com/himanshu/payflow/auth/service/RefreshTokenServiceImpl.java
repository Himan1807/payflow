package com.himanshu.payflow.auth.service;

import com.himanshu.payflow.auth.entity.AppUser;
import com.himanshu.payflow.auth.entity.RefreshToken;
import com.himanshu.payflow.auth.exception.RefreshTokenExpiredException;
import com.himanshu.payflow.auth.exception.RefreshTokenNotFoundException;
import com.himanshu.payflow.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    @Transactional
    @Override
    public RefreshToken createRefreshToken(AppUser user) {
        repository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusSeconds(refreshExpiration / 1000))
                .user(user)
                .build();

        return repository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found"));

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            repository.delete(refreshToken);
            throw new RefreshTokenExpiredException("Refresh token expired");
        }
        return refreshToken;
    }

    @Override
    public void deleteByUser(AppUser user) {
        repository.deleteByUser(user);
    }
}