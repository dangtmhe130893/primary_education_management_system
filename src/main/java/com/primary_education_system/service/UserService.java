package com.primary_education_system.service;

import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsernameAndIsDeletedFalse(username);
    }

    public Page<UserEntity> getListAccount(Pageable pageable, String keyword) {
        return userRepository.getListAccount(keyword, pageable);
    }

}
