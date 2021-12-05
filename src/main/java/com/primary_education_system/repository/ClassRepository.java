package com.primary_education_system.repository;

import com.primary_education_system.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    @Query(value = "select c from ClassEntity c where " +
            "c.name like %?1% and c.isDeleted = false")
    Page<ClassEntity> getPage(String keyword, Pageable pageable);

    ClassEntity findByIdAndIsDeletedFalse(Long classId);

    List<ClassEntity> findByIsDeletedFalse();

    List<ClassEntity> findByGradeAndIsDeletedFalse(String grade);

    List<ClassEntity> findByIdInAndIsDeletedFalse(List<Long> listClassId);

    ClassEntity findBySeoAndIsDeletedFalse(String seoNameClass);

    @Query(value = "select c from ClassEntity c " +
            "where c.grade in ?1 and c.isDeleted = false")
    List<ClassEntity> findByListGrade(List<String> listGrade);

    ClassEntity findByHomeroomTeacherIdAndIsDeletedFalse(Long homeroomTeacherId);

    @Query(value = "select c.name from ClassEntity c " +
            "where c.grade = ?1 and c.isDeleted = false")
    Set<String> getSetClassNameByGrade(String grade);

    @Query(value = "select c from ClassEntity c " +
            "where c.name in ?1 and c.isDeleted = false")
    List<ClassEntity> findByListNameClass(List<String> listNameClass);
}
