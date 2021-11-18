package com.primary_education_system.entity;

import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.enum_type.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "time_schedule")
public class TimeScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private Long frameTimeId;
    private Long subjectId;
    private Long teacherId;
    private Long classId;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;

    @Transient
    private String nameSubject;

    @Transient
    private String nameTeacher;

    @Transient
    private String nameFrameTime;

    @Transient
    private List<UserEntity> listTeacher;

    public TimeScheduleEntity() {
    }

    public TimeScheduleEntity(DayOfWeek dayOfWeek, Long frameTimeId, Long subjectId, Long teacherId, Long classId) {
        this.dayOfWeek = dayOfWeek;
        this.frameTimeId = frameTimeId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.classId = classId;
    }
}
