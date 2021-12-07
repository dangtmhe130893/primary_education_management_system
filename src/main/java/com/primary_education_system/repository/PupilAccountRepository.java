package com.primary_education_system.repository;

import com.primary_education_system.dto.pupil_account.ClassIdAndNumberPupil;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Query(value = "select p.email from PupilAccountEntity p " +
            "where p.isDeleted = false")
    Set<String> getSetEmailExist();

    PupilAccountEntity findByEmailAndIsDeletedFalse(String email);

    @Query(value = "select count(p.id) from PupilAccountEntity p " +
            "where p.isDeleted = false")
    long countTotalPupil();

    @Query(value = "select p.grade, count(p.id) from PupilAccountEntity p " +
            "where p.isDeleted = false group by p.grade")
    List<Object[]> getObjectGradeAndNumberPupil();

    List<PupilAccountEntity> findByIdInAndIsDeletedFalse(Collection<Long> listTuitionId);

    List<PupilAccountEntity> findByClassIdAndIsDeletedFalse(Long classId);

    @Query(value = "select p from PupilAccountEntity p " +
            "where p.name like concat('%', ?1, '%') and p.isDeleted = false")
    List<PupilAccountEntity> findByKeywordAndIsDeletedFalse(String keyword);

    @Query(value = "select count(p.id) from PupilAccountEntity p " +
            "where p.isDeleted = false and p.email = ?1")
    int countByEmail(String email);

    @Query(value = "select count(p.id) from PupilAccountEntity p " +
            "where p.isDeleted = false and p.email = ?1 and p.id <> ?2")
    int countByEmailAndId(String email, Long id);
}
