package com.primary_education_system.dto.time_schedule;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoTimeScheduleTeacherDto {
    private String nameClass;
    private String seoNameClass;
    private int numberPupil;
    private String nameRoom;
    private Long classId;

    public InfoTimeScheduleTeacherDto(Long classId) {
        this.classId = classId;
    }
}
