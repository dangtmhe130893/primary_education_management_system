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

import java.util.Date;

@Service
public class ClassService {
    @Autowired
    ClassRepository classRepository;

    public Page<ClassEntity> getPage(Pageable pageable) {
        return classRepository.getPage(pageable);
    }

    public ServerResponseDto save(ClassDto classDto) {
        Long classId = classDto.getId();

        ClassEntity classEntity;
        if (classId != null) {
            classEntity = classRepository.findByIdAndIsDeletedFalse(classId);
            classEntity.setUpdatedTime(new Date());
        } else {
            classEntity = new ClassEntity();
            classEntity.setCreatedTime(new Date());
            classEntity.setUpdatedTime(new Date());
        }
        classEntity.setName(classDto.getNameClass());
        classEntity.setGrade(classDto.getGrade());
        classEntity.setRoom(classDto.getRoom());

        classRepository.save(classEntity);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto detail(Long id) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(id);
        if(classEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, classEntity);
    }

    public ServerResponseDto delete(Long id) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(id);
        if(classEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        classEntity.setDeleted(true);
        classRepository.save(classEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }
}
