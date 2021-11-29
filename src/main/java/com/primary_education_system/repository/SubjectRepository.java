package com.primary_education_system.repository;

import com.primary_education_system.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    List<SubjectEntity> findByIsDeletedFalse();

    @Query(value = "select s from SubjectEntity s " +
            "where s.isDeleted = false and s.name like concat('%', ?1, '%')")
    Page<SubjectEntity> getPage(String keyword, Pageable pageable);

    SubjectEntity findByIdAndIsDeletedFalse(Long subjectId);

    List<SubjectEntity> findByIdInAndIsDeletedFalse(List<Long> listSubjectId);

    @Query(value = "select s.name from SubjectEntity s " +
            "where s.id = ?1 and s.isDeleted = false")
    String getNameSubjectById(Long teachSubjectId);

    @Query(value = "select s.* from subject as s " +
            "inner join subject_teacher as sb on s.id = sb.subject_id " +
            "inner join user as u on u.id = sb.teacher_id " +
            "where u.id = ?1 and u.is_deleted = false and s.is_deleted = false", nativeQuery = true)
    List<SubjectEntity> getListSubjectByTeacherId(Long userId);
}
