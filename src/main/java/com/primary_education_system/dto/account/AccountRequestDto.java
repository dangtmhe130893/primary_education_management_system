package com.primary_education_system.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestDto {
    private String name;
    private Long id;
    private String email;
    private String password;
    private String phone;
    private String listRoleName;

}
