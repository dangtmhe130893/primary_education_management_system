package com.primary_education_system.repository;

import com.primary_education_system.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    List<GradeEntity> findByIsDeletedFalse();

    @Query(value = "select g.name from GradeEntity g where g.isDeleted = false")
    List<String> getAllNameGrade();
}
