package com.primary_education_system.repository;

import com.primary_education_system.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    @Query(value = "select c from ClassEntity c where c.isDeleted = false")
    Page<ClassEntity> getPage(Pageable pageable);

    ClassEntity findByIdAndIsDeletedFalse(Long classId);
}
