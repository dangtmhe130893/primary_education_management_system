package com.primary_education_system.repository;

import com.primary_education_system.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailAndIsDeletedFalse(String username);
    UserEntity findByIdAndIsDeletedFalse(Long id);

    @Query(value = "select u from UserEntity u " +
            "where u.isDeleted = false and u.id <> 1 and (u.name like %?1% or u.email like %?1%)")
    Page<UserEntity> getListAccount(String keyword, Pageable pageable);

    @Query(value = "select count(u) from UserEntity u " +
            "where u.email = ?1 and u.id <> ?2 and u.isDeleted = false ")
    int countByEmailAndId(String email, Long id);

    @Query(value = "select u from UserEntity u " +
            "where u.teachSubjectId in ?1 and u.isDeleted = false")
    List<UserEntity> getListTeacherByListSubjectId(List<Long> listSubjectId);

    @Query(value = "select u.* from user as u " +
            "inner join user_role on u.id = user_role.user_id " +
            "inner join role on role.id = user_role.role_id " +
            "where u.is_deleted = false and u.status_user = 2 " +
            "and role.name = 'TEACHER' " +
            "and u.teach_subject_id is null", nativeQuery = true)
    List<UserEntity> getListTeacherCanTeach();

    @Query(value = "select u.* from user as u " +
            "inner join user_role on u.id = user_role.user_id " +
            "inner join role on role.id = user_role.role_id " +
            "where u.is_deleted = false and u.status_user = 2 " +
            "and u.id in ?1 " +
            "and role.name = ?2", nativeQuery = true)
    List<UserEntity> getListUserByListIdAndRole(List<Long> listTeacherId, String role);

    @Query(value = "select u.* from user as u " +
            "inner join user_role on u.id = user_role.user_id " +
            "inner join role on role.id = user_role.role_id " +
            "where u.is_deleted = false and u.status_user = 2 " +
            "and u.teach_subject_id = ?1 " +
            "and role.name = 'TEACHER'", nativeQuery = true)
    List<UserEntity> getListTeacherBySubjectId(Long subjectId);

}
