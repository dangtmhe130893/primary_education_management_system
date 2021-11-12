package com.primary_education_system.repository;

import com.primary_education_system.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameAndIsDeletedFalse(String username);

}
