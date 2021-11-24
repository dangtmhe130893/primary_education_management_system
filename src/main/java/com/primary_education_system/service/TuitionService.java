package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.entity.TuitionEntity;
import com.primary_education_system.repository.TuitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class TuitionService {

    @Autowired
    private TuitionRepository tuitionRepository;

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

}
