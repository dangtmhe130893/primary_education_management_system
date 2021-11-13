package com.primary_education_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "frame_time_schedule")
public class FrameTimeScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;
}
