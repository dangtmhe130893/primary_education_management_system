package com.primary_education_system.dto.time_schedule;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimeScheduleRequestDto {
    private Long timeScheduleId;
    private Long subjectId;
    private Long teacherId;
}
