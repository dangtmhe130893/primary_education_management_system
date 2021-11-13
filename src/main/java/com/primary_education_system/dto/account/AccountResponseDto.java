package com.primary_education_system.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountResponseDto {
    private String name;
    private Long id;
    private String email;
    private String phone;
    private Integer statusUser;
    private Date birthday;
    private String address;

}
