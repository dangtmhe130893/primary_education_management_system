package com.primary_education_system.repository;

import com.primary_education_system.entity.SubjectEntity;
import com.primary_education_system.entity.SubjectTeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacherEntity, Long> {
    @Query(value = "select st from SubjectTeacherEntity st " +
            "where st.subjectId in ?1")
    List<SubjectTeacherEntity> findByListSubjectId(List<Long> listSubjectId);

    @Modifying
    @Query(value = "delete from SubjectTeacherEntity s " +
            "where s.subjectId = ?1")
    void deleteBySubjectId(Long subjectId);

    List<SubjectTeacherEntity> findBySubjectId(Long subjectId);

    @Modifying
    @Query(value = "delete from SubjectTeacherEntity s " +
            "where s.subjectId = ?1 and s.teacherId in ?2")
    void deleteBySubjectIdAndListTeacherId(Long subjectId, List<Long> listTeacherIdRemoved);
}
