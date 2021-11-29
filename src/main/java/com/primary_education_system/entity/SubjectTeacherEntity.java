package com.primary_education_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "subject_teacher")
public class SubjectTeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long subjectId;
    private Long teacherId;

    public SubjectTeacherEntity() {
    }

    public SubjectTeacherEntity(Long subjectId, Long teacherId) {
        this.subjectId = subjectId;
        this.teacherId = teacherId;
    }
}
