package com.primary_education_system.entity;

import com.primary_education_system.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "class")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String grade;
    private Long roomId;
    private String seo;
    private Long homeroomTeacherId;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;

    @Transient
    private String roomName;

    @Transient
    private String homeroomTeacher;

    @Transient
    private UserEntity homeroomTeacherCurrent;

    @Transient
    private RoomEntity roomCurrent;

}
