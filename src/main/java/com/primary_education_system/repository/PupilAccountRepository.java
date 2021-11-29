package com.primary_education_system.repository;

import com.primary_education_system.dto.pupil_account.ClassIdAndNumberPupil;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PupilAccountRepository extends JpaRepository<PupilAccountEntity, Long> {

    @Query(value = "select p from PupilAccountEntity p " +
            "where p.isDeleted = false and (p.name like %?1% or p.email like %?1% or p.code like %?1% ) " +
            "and p.grade in ?2 " +
            "and p.classId in ?3")
    Page<PupilAccountEntity> getPagePupilAccount(String keyword, List<String> listNameGrade, List<Long> listClassId, Pageable pageable);

    PupilAccountEntity findByIdAndIsDeletedFalse(Long id);

    @Query(value = "select p.classId as classId, count(p.id) as numberPupil from PupilAccountEntity p " +
            "where p.isDeleted = false group by p.classId having p.classId in ?1")
    List<ClassIdAndNumberPupil> getClassIdAndNumberPupil(List<Long> listClassId);

    @Query(value = "select count(p.id) from PupilAccountEntity p " +
            "where p.classId = ?1 and p.isDeleted = false")
    long countNumberPupilInClass(Long classId);
}
