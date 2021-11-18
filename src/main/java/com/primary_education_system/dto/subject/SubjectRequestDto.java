package com.primary_education_system.dto.subject;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SubjectRequestDto {
    private Long id;
    private String subject;
    private String listTeacherIdString;
}
