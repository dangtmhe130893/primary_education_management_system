package com.primary_education_system.repository;


import com.primary_education_system.entity.TimeScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeScheduleRepository extends JpaRepository<TimeScheduleEntity, Long> {
    List<TimeScheduleEntity> findByClassIdAndIsDeletedFalse(Long classId);
}
