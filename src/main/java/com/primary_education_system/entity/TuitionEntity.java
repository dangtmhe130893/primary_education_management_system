package com.primary_education_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tuition")
public class TuitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String grade;
    private int fee;

    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;
}
