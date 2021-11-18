package com.primary_education_system.entity;

import com.primary_education_system.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Transient
    private List<UserEntity> listTeacherTeaching;

    @Transient
    private List<UserEntity> listTeacherCanTeach;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;
}
