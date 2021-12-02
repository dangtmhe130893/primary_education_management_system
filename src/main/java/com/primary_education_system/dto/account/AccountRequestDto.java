package com.primary_education_system.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountRequestDto {
    private String name;
    private Long id;
    private String email;
    private String password;
    private String phone;
    private String listRoleName;
    private String birthday;
    private String address;
    private int sex;

}
