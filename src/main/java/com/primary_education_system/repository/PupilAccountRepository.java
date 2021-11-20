package com.primary_education_system.repository;

import com.primary_education_system.entity.pupil.PupilAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PupilAccountRepository extends JpaRepository<PupilAccountEntity, Long> {

    @Query(value = "select p from PupilAccountEntity p " +
            "where p.isDeleted = false and (p.name like %?1% or p.email like %?1%)")
    Page<PupilAccountEntity> getPagePupilAccount(String keyword, Pageable pageable);

}
