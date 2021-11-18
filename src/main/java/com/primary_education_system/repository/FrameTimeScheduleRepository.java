package com.primary_education_system.repository;

import com.primary_education_system.entity.FrameTimeScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrameTimeScheduleRepository extends JpaRepository<FrameTimeScheduleEntity, Long> {
    List<FrameTimeScheduleEntity> findByIsDeletedFalse();

    FrameTimeScheduleEntity findByIdAndIsDeletedFalse(Long frameTimeId);
}
