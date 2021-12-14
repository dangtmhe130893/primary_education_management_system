package com.primary_education_system.dto.pupil_account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PupilAccountImportDto that = (PupilAccountImportDto) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
