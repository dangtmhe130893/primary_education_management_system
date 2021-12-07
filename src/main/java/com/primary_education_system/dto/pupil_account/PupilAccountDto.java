package com.primary_education_system.dto.pupil_account;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PupilAccountDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String birthday;
    private int sex;
    private String address;
    private String fatherName;
    private String motherName;

    private String grade;
    private Long classId;

}
