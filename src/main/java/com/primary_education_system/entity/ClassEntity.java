package com.primary_education_system.entity;

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
    private String room;
    private String seo;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;
}
