package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.pupil_account.HistoryPayTuitionDto;
import com.primary_education_system.entity.pupil.HistoryPayTuitionEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.repository.HistoryPayTuitionRepository;
import com.primary_education_system.repository.TuitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryPayTuitionService {

    @Autowired
    private HistoryPayTuitionRepository historyPayTuitionRepository;

    @Autowired
    private TuitionRepository tuitionRepository;

    @Autowired
    private PupilAccountService pupilAccountService;

    public ServerResponseDto findByPupilId(Long pupilId) {
        PupilAccountEntity pupil = pupilAccountService.findById(pupilId);
        if (pupil == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        HistoryPayTuitionDto result = new HistoryPayTuitionDto();
        List<HistoryPayTuitionEntity> history = historyPayTuitionRepository.findByPupilId(pupilId);
        result.setHistory(history);
        result.setTotal(tuitionRepository.findTop1ByGradeAndIsDeletedFalse(pupil.getGrade()).getFee());
        return new ServerResponseDto(ResponseCase.SUCCESS, result);
    }

}
