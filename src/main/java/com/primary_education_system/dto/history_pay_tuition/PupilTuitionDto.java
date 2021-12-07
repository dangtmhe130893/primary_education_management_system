package com.primary_education_system.dto.history_pay_tuition;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PupilTuitionDto {
    private String pupilName;
    private int tuitionTotal;
    private int tuitionRemain;

    public PupilTuitionDto(String pupilName, int tuitionTotal, int tuitionRemain) {
        this.pupilName = pupilName;
        this.tuitionTotal = tuitionTotal;
        this.tuitionRemain = tuitionRemain;
    }
}
