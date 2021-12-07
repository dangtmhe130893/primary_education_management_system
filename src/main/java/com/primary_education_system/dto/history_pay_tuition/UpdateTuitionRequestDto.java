package com.primary_education_system.dto.history_pay_tuition;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateTuitionRequestDto {
    private Long pupilId;
    private Long tuitionAdd;
}
