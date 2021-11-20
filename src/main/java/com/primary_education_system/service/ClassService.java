package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ResponseStatus;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.classs.ClassDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ClassService {
    @Autowired
    ClassRepository classRepository;

    @Autowired
    TimeScheduleService timeScheduleService;

    public Page<ClassEntity> getPage(Pageable pageable, String keyword) {
        return classRepository.getPage(keyword, pageable);
    }

    @Transactional
    public ServerResponseDto save(ClassDto classDto) {
        Long classId = classDto.getId();
        boolean isUpdate = classId != null;

        ClassEntity classEntity;
        if (isUpdate) {
            classEntity = classRepository.findByIdAndIsDeletedFalse(classId);
        } else {
            classEntity = new ClassEntity();
            classEntity.setCreatedTime(new Date());
        }
        classEntity.setUpdatedTime(new Date());
        classEntity.setName(classDto.getNameClass());
        classEntity.setGrade(classDto.getGrade());
        classEntity.setRoom(classDto.getRoom());

        classEntity = classRepository.save(classEntity);

        if (!isUpdate) {
            /* create time schedule */
            timeScheduleService.createTimeSchedule(classEntity.getId());
        }

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto detail(Long id) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(id);
        if (classEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, classEntity);
    }

    public ServerResponseDto delete(Long id) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(id);
        if (classEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        classEntity.setDeleted(true);
        classRepository.save(classEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto getList() {
        return new ServerResponseDto(ResponseCase.SUCCESS, classRepository.findByIsDeletedFalse());
    }
}
