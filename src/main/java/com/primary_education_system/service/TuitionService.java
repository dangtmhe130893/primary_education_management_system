package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.entity.TuitionEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.repository.TuitionRepository;
import com.primary_education_system.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TuitionService {

    @Autowired
    private TuitionRepository tuitionRepository;

    @Autowired
    private PupilAccountService pupilAccountService;

    @Autowired
    private ClassService classService;

    public Page<TuitionEntity> getPage(Pageable pageable) {
        return tuitionRepository.getPage(pageable);
    }

    @Transactional
    public ServerResponseDto save(TuitionEntity tuitionEntityRequest) {
        Long tuitionId = tuitionEntityRequest.getId();
        boolean isUpdate = tuitionId != null;

        TuitionEntity tuitionEntity;
        if (isUpdate) {
            tuitionEntity = tuitionRepository.findByIdAndIsDeletedFalse(tuitionId);
        } else {
            tuitionEntity = new TuitionEntity();
            tuitionEntity.setCreatedTime(new Date());
        }
        tuitionEntity.setUpdatedTime(new Date());
        tuitionEntity.setGrade(tuitionEntityRequest.getGrade());
        tuitionEntity.setFee(tuitionEntityRequest.getFee());

        tuitionRepository.save(tuitionEntity);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto detail(Long id) {
        TuitionEntity tuitionEntity = tuitionRepository.findByIdAndIsDeletedFalse(id);
        if (tuitionEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, tuitionEntity);
    }

    public int getTotalTuitionRequire() {
        Map<String, Integer> mapTuitionByGrade = getMapTuitionByGrade();
        Map<String, Integer> mapNumberPupilByGrade = pupilAccountService.getMapNumberPupilByGrade();

        int totalTuition = Constant.setGrade
                .stream()
                .mapToInt(grade -> mapTuitionByGrade.getOrDefault(grade, 0)
                        * mapNumberPupilByGrade.getOrDefault(grade, 0)).sum();
        return totalTuition;
    }

    public Map<Long, Integer> getMapTuitionByClassId(List<Long> listClassId) {
        Map<String, Integer> mapTuitionByGrade = getMapTuitionByGrade();
        Map<Long, String> mapGradeByClassId = classService.getMapGradeByClassId(listClassId);

        Map<Long, Integer> result = new HashMap<>(listClassId.size());
        listClassId.forEach(classId -> {
            result.put(classId, mapTuitionByGrade.getOrDefault(mapGradeByClassId.get(classId), 0));
        });

        return result;
    }

    public Map<Long, Integer> getMapTuitionPupilByPupilId(List<PupilAccountEntity> listPupil) {
        Map<String, Integer> mapTuitionByGrade = getMapTuitionByGrade();

        Map<Long, String> mapGradeByPupilId = listPupil
                .stream()
                .collect(Collectors.toMap(PupilAccountEntity::getId, PupilAccountEntity::getGrade));
        List<Long> listPupilId = listPupil
                .stream()
                .map(PupilAccountEntity::getId)
                .collect(Collectors.toList());

        Map<Long, Integer> mapTuitionPupilByPupilId = new HashMap<>(listPupil.size());
        listPupilId.forEach(pupilId -> {
            mapTuitionPupilByPupilId.put(pupilId, mapTuitionByGrade.getOrDefault(mapGradeByPupilId.get(pupilId), 0));
        });
        return mapTuitionPupilByPupilId;
    }

    public Map<String, Integer> getMapTuitionByGrade() {
        List<TuitionEntity> listTuition = tuitionRepository.findByIsDeletedFalse();
        return listTuition
                .stream()
                .collect(Collectors.toMap(TuitionEntity::getGrade, TuitionEntity::getFee));
    }

    public int getTuitionByGrade(String grade) {
        TuitionEntity tuitionEntity = tuitionRepository.findByGradeAndIsDeletedFalse(grade);
        return tuitionEntity != null ? tuitionEntity.getFee() : 0;
    }
}
