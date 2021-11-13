package com.primary_education_system.repository;

import com.primary_education_system.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailAndIsDeletedFalse(String username);
    UserEntity findByIdAndIsDeletedFalse(Long id);

    @Query(value = "select u from UserEntity u " +
            "where u.isDeleted = false and u.id <> 1 and (u.name like %?1% or u.email like %?1%)")
    Page<UserEntity> getListAccount(String keyword, Pageable pageable);

    @Query(value = "select count(u) from UserEntity u " +
            "where u.email = ?1 and u.id <> ?2 and u.isDeleted = false ")
    int countByEmailAndId(String email, Long id);

}
