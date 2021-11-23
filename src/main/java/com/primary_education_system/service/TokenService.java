package com.primary_education_system.service;

import com.primary_education_system.entity.token.TokenEntity;
import com.primary_education_system.entity.token.TokenType;
import com.primary_education_system.repository.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    public String generateToken() {
        String token = UUID.randomUUID().toString();
        String generateToken = generateToken(token);
        String timeNow = String.valueOf(System.currentTimeMillis());
        return generateToken + timeNow;
    }

    private String generateToken(String token) {
        final TokenEntity myToken = new TokenEntity(token);
        return token + myToken.hashCode();
    }

    public void save(TokenEntity token) {
        try {
            tokenRepository.save(token);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Cannot save token");
        }
    }

    public TokenEntity validateToken(String token, TokenType tokenType) {
        return tokenRepository.findByTokenAndTokenType(token, tokenType);
    }
}
