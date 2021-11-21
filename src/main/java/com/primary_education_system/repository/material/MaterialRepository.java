package com.primary_education_system.repository.material;

import com.primary_education_system.entity.material.MaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {

    @Query(value = "select m from MaterialEntity m where " +
            "m.name like %?1% and m.isDeleted = false")
    Page<MaterialEntity> getPage(String keyword, Pageable pageable);

    MaterialEntity findByIdAndIsDeletedFalse(Long id);

    int countByCode(String code);

}
