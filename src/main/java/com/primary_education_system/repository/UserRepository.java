package com.primary_education_system.repository;

import com.primary_education_system.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameAndIsDeletedFalse(String username);

    @Query(value = "Select u from UserEntity u where u.isDeleted = false and u.id <> 1 " +
            "and (u.name like %?1% or u.email like %?1%)")
    Page<UserEntity> getListAccount(String keyword, Pageable pageable);

}
