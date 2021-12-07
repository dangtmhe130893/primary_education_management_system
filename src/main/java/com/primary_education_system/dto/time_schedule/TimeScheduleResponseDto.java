package com.primary_education_system.dto.time_schedule;

import com.primary_education_system.entity.TimeScheduleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TimeScheduleResponseDto {
    private String homeroomTeacher;
    private String roomNameDefault;
    private List<TimeScheduleEntity> listTimeSchedule;
    private String className;

    public TimeScheduleResponseDto() {
    }

    public TimeScheduleResponseDto(String homeroomTeacher, String roomNameDefault,
                                   String className,
                                   List<TimeScheduleEntity> listTimeSchedule) {
        this.homeroomTeacher = homeroomTeacher;
        this.roomNameDefault = roomNameDefault;
        this.listTimeSchedule = listTimeSchedule;
        this.className = className;
    }
}
