package com.primary_education_system.repository.material;

import com.primary_education_system.entity.material.MaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {

    @Query(value = "select m from MaterialEntity m where " +
            "(m.name like %?1% or m.code like %?1%) and m.subjectId in ?2 and m.grade like %?3% and m.type like %?4% " +
            "and m.isDeleted = false")
    Page<MaterialEntity> getPage(String keyword, Collection<Long> subjectIds, String grade, String type, Pageable pageable);

    MaterialEntity findByIdAndIsDeletedFalse(Long id);

    MaterialEntity findByCodeAndIsDeletedFalse(String code);

    int countByCode(String code);

}
