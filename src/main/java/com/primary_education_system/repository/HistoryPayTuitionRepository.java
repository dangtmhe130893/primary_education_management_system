package com.primary_education_system.repository;

import com.primary_education_system.dto.history_pay_tuition.PupilIdAndTuitionSubmitted;
import com.primary_education_system.entity.pupil.HistoryPayTuitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryPayTuitionRepository extends JpaRepository<HistoryPayTuitionEntity, Long> {

    List<HistoryPayTuitionEntity> findByPupilId(Long pupilIs);

    @Query(value = "select sum(h.quantity) from history_pay_tuition as h", nativeQuery = true)
    Long getTotalTuitionReceived();

    @Query(value = "select h.pupil_id as pupilId, sum(h.quantity) as tuitionSubmitted from history_pay_tuition as h group by h.pupil_id", nativeQuery = true)
    List<PupilIdAndTuitionSubmitted> getListObjectTuitionIdAndTotalTuitionSubmitted();

    @Query(value = "select h.pupil_id as pupilId, sum(h.quantity) as tuitionSubmitted from history_pay_tuition as h " +
            "where h.pupil_id in ?1 group by h.pupil_id", nativeQuery = true)
    List<PupilIdAndTuitionSubmitted> getListObjectTuitionIdAndTotalTuitionSubmitted(List<Long> listPupilId);

    @Query(value = "select sum(h.quantity) from history_pay_tuition as h " +
            "where h.pupil_id = ?1", nativeQuery = true)
    Long getTuitionSubmittedByPupilId(Long pupilId);
}
