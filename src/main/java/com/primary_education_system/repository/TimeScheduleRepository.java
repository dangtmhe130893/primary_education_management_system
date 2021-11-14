package com.primary_education_system.repository;


import com.primary_education_system.entity.TimeScheduleEntity;
import com.primary_education_system.enum_type.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeScheduleRepository extends JpaRepository<TimeScheduleEntity, Long> {
    List<TimeScheduleEntity> findByClassIdAndIsDeletedFalse(Long classId);

    @Query(value = "select t from TimeScheduleEntity t " +
            "where t.dayOfWeek = ?1 and t.classId = ?2 and t.isDeleted = false " +
            "order by t.frameTimeId asc")
    List<TimeScheduleEntity> getByDayOfWeekAndClassId(DayOfWeek dayOfWeek, Long classId);
}
