package com.primary_education_system.repository.material;

import com.primary_education_system.entity.material.ClassMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassMaterialRepository extends JpaRepository<ClassMaterialEntity, Long> {
    @Query(value = "select cm from ClassMaterialEntity cm " +
            "where cm.materialId in ?1")
    List<ClassMaterialEntity> findByMaterialIdIn(List<Long> listMaterialId);

    @Query(value = "select cm from ClassMaterialEntity cm " +
            "where cm.materialId = ?1")
    List<ClassMaterialEntity> getByMaterialId(Long materialId);

    @Modifying
    @Query(value = "delete from ClassMaterialEntity cm " +
            "where cm.classId in ?1")
    void deleteByListClassId(List<Long> listClassIdRemoved);
}
