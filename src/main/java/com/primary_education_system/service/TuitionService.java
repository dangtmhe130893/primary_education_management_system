package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.classs.ClassDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.TuitionEntity;
import com.primary_education_system.repository.ClassRepository;
import com.primary_education_system.repository.TuitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TuitionService {

    @Autowired
    private TuitionRepository tuitionRepository;

    @Autowired
    private TimeScheduleService timeScheduleService;

    public Page<TuitionEntity> getPage(Pageable pageable, String keyword) {
        return tuitionRepository.getPage(keyword, pageable);
    }

    @Transactional
    public ServerResponseDto save(TuitionEntity tuitionEntity) {
        Long tuitionId = tuitionEntity.getId();
        boolean isUpdate = tuitionId != null;


        if (isUpdate) {
            tuitionEntity = tuitionRepository.findByIdAndIsDeletedFalse(tuitionId);
        } else {
            tuitionEntity = new TuitionEntity();
            tuitionEntity.setCreatedTime(new Date());
        }
        tuitionEntity.setUpdatedTime(new Date());
        tuitionEntity.setGrade(tuitionEntity.getGrade());


        tuitionEntity = tuitionRepository.save(tuitionEntity);

        if (!isUpdate) {
            /* create time schedule */
            timeScheduleService.createTimeSchedule(tuitionEntity.getId());
        }

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto detail(Long id) {
        TuitionEntity tuitionEntity = tuitionRepository.findByIdAndIsDeletedFalse(id);
        if (tuitionEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, tuitionEntity);
    }

    @Transactional
    public ServerResponseDto delete(Long classId) {
        TuitionEntity tuitionEntity = tuitionRepository.findByIdAndIsDeletedFalse(classId);
        if (tuitionEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        tuitionEntity.setDeleted(true);
        tuitionRepository.save(tuitionEntity);

        /* delete Time schedule */
        timeScheduleService.deleteTimeScheduleByClassId(classId);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto getList() {
        return new ServerResponseDto(ResponseCase.SUCCESS, tuitionRepository.findByIsDeletedFalse());
    }

    public ServerResponseDto getListByGradeIdStr(String grade) {
        List<TuitionEntity> listTuition;
        if ("0".equals(grade)) {
            System.out.println("testtt");
            listTuition = tuitionRepository.findByIsDeletedFalse();
        } else {
            listTuition = tuitionRepository.findByGradeAndIsDeletedFalse(grade);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, listTuition);
    }

    public List<TuitionEntity> getListByGrade(String grade) {
        return tuitionRepository.findByGradeAndIsDeletedFalse(grade);
    }

    public Map<Long, String> getMapGradeByTuitionId(List<Long> listClassId) {
        List<TuitionEntity> listTuitionEntity = tuitionRepository.findByIdInAndIsDeletedFalse(listClassId);
        return listTuitionEntity
                .stream()
                .collect(Collectors.toMap(TuitionEntity::getId, TuitionEntity::getGrade));
    }


}
