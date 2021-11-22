package com.primary_education_system.dto.account;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}
