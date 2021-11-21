package com.primary_education_system.entity.material;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "material")
@Getter
@Setter
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long subjectId;
    private String grade;
    private String name;
    private String code;
    private String type;
    private String content;
    private String fileName;
    private String linkFile;
    private Long createdByUserId;
    private Long updatedByUserId;
    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;

    @Transient
    private String subjectName;

}
