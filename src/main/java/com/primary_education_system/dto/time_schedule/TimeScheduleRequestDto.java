package com.primary_education_system.dto.time_schedule;

import com.primary_education_system.enum_type.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TimeScheduleRequestDto {
    private Long timeScheduleId;
    private Long subjectId;
    private Long teacherId;
}
