package com.primary_education_system.dto.history_pay_tuition;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RevenueTuitionDto {
    private String grade;

    private String className;

    private Long pupilId;
    private String pupilName;

    private Integer numberPupil;
    private Integer tuitionRequire;
    private Integer tuitionReceived;
    private boolean isDoneTuition;

    public RevenueTuitionDto() {
    }

    public RevenueTuitionDto(Integer numberPupil, Integer tuitionRequire, Integer tuitionReceived, boolean isDoneTuition) {
        this.numberPupil = numberPupil;
        this.tuitionRequire = tuitionRequire;
        this.tuitionReceived = tuitionReceived;
        this.isDoneTuition = isDoneTuition;
    }


}
