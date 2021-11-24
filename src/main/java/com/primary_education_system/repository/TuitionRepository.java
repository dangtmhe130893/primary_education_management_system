package com.primary_education_system.repository;

import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.TuitionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuitionRepository extends JpaRepository<TuitionEntity, Long > {

    @Query(value = "select t from TuitionEntity t where " +
            "t.grade like %?1% and t.isDeleted = false")
    Page<TuitionEntity> getPage(String keyword, Pageable pageable);

    TuitionEntity findByIdAndIsDeletedFalse(Long tuitionId);

    List<TuitionEntity> findByIsDeletedFalse();

    List<TuitionEntity> findByGradeAndIsDeletedFalse(String grade);

    List<TuitionEntity> findByIdInAndIsDeletedFalse(List<Long> listtuitionId);
}
