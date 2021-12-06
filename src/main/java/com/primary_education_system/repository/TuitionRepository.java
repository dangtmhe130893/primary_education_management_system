package com.primary_education_system.repository;

import com.primary_education_system.entity.TuitionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TuitionRepository extends JpaRepository<TuitionEntity, Long> {

    @Query(value = "select t from TuitionEntity t where t.isDeleted = false")
    Page<TuitionEntity> getPage(Pageable pageable);

    TuitionEntity findByIdAndIsDeletedFalse(Long tuitionId);

    TuitionEntity findTop1ByGradeAndIsDeletedFalse(String grade);

}
