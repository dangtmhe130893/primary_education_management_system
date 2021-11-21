package com.primary_education_system.entity.pupil;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pupil_account")
@Getter
@Setter
public class PupilAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String rawPassword;

    private String name;
    private String username;

    private String grade;
    private Long classId;

    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;
    private int gender; //1 male, 2 female, 9 other
    private String address;
    private String fatherName;
    private String motherName;
    private boolean isDeleted;
    private Date createdTime;
    private Date updatedTime;
    private boolean isChangePassword;

    @Transient
    private String className;

    private Integer statusUser; //1 un_active, 2 active

}
