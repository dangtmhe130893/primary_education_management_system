package com.primary_education_system.repository;

import com.primary_education_system.entity.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
}

