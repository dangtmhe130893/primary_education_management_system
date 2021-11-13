package com.primary_education_system.entity;

import com.primary_education_system.enum_type.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private String subject;
    private Long classId;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;

    public TimeScheduleEntity() {
    }

    public TimeScheduleEntity(DayOfWeek dayOfWeek, Long frameTimeId, String subject, Long classId) {
        this.dayOfWeek = dayOfWeek;
        this.frameTimeId = frameTimeId;
        this.subject = subject;
        this.classId = classId;
    }
}
