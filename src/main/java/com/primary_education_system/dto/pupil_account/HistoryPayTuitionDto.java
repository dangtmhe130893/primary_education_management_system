package com.primary_education_system.dto.pupil_account;

import com.primary_education_system.entity.pupil.HistoryPayTuitionEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HistoryPayTuitionDto {

    private Integer total;
    private List<HistoryPayTuitionEntity> history;

}
