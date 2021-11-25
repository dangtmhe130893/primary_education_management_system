package com.primary_education_system.repository;


import com.primary_education_system.dto.time_schedule.InfoTimeScheduleTeacherDto;
import com.primary_education_system.entity.TimeScheduleEntity;
import com.primary_education_system.enum_type.DayOfWeek;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeScheduleRepository extends JpaRepository<TimeScheduleEntity, Long> {
    List<TimeScheduleEntity> findByClassIdAndIsDeletedFalse(Long classId);
    List<TimeScheduleEntity> findByTeacherIdAndIsDeletedFalse(Long teacherId);

    @Query(value = "select t from TimeScheduleEntity t " +
            "where t.dayOfWeek = ?1 and t.classId = ?2 and t.isDeleted = false " +
            "order by t.frameTimeId asc")
    List<TimeScheduleEntity> getByDayOfWeekAndClassId(DayOfWeek dayOfWeek, Long classId);

    TimeScheduleEntity findByIdAndIsDeletedFalse(Long timeScheduleId);

    @Query(value = "select count(t.id) from TimeScheduleEntity t " +
            "where t.classId <> ?1 and t.dayOfWeek = ?2 and t.frameTimeId = ?3 and t.teacherId = ?4 " +
            "and t.isDeleted = false")
    long countSameTimeSchedule(Long classId, DayOfWeek dayOfWeek, Long frameTimeId, Long teacherId);

    @Query(value = "select distinct new com.primary_education_system.dto.time_schedule.InfoTimeScheduleTeacherDto(t.classId) " +
            "from TimeScheduleEntity t " +
            "where t.teacherId = ?1 and t.isDeleted = false group by t.classId")
    Page<InfoTimeScheduleTeacherDto> getInfoTimeScheduleTeacher(Long teacherId, Pageable pageable);
}
