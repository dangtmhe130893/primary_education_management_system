package com.primary_education_system.service;

import com.primary_education_system.entity.pupil.HistoryPayTuitionEntity;
import com.primary_education_system.repository.HistoryPayTuitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryPayTuitionService {

    @Autowired
    private HistoryPayTuitionRepository historyPayTuitionRepository;

    public List<HistoryPayTuitionEntity> findByPupilId(Long pupilId) {
        return historyPayTuitionRepository.findByPupilId(pupilId);
    }

}
