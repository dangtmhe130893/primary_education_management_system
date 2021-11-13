package com.primary_education_system.repository;

import com.primary_education_system.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    List<GradeEntity> findByIsDeletedFalse();
}
