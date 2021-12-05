package com.primary_education_system.dto.pupil_account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PupilAccountImportDto {
    private String name;
    private String grade;
    private String className;
    private String email;
    private String password;
    private String phone;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;
    private String gender;
    private String address;
    private String fatherName;
    private String motherName;
}
