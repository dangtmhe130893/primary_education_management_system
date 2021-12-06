package com.primary_education_system.repository;

import com.primary_education_system.entity.pupil.HistoryPayTuitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryPayTuitionRepository extends JpaRepository<HistoryPayTuitionEntity, Long> {

    List<HistoryPayTuitionEntity> findByPupilId(Long pupilIs);

}
