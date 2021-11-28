package com.primary_education_system.dto.classs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassDto {
    private Long id;
    private String grade;
    private Long roomId;
    private String nameClass;

    private Long homeroomTeacherId;
}
