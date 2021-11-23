package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TimeScheduleService timeScheduleService;

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

    @Transactional
    public ServerResponseDto delete(Long classId) {
        ClassEntity classEntity = classRepository.findByIdAndIsDeletedFalse(classId);
        if (classEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        classEntity.setDeleted(true);
        classRepository.save(classEntity);

        /* delete Time schedule */
        timeScheduleService.deleteTimeScheduleByClassId(classId);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto getList() {
        return new ServerResponseDto(ResponseCase.SUCCESS, classRepository.findByIsDeletedFalse());
    }

    public ServerResponseDto getListByGradeIdStr(String grade) {
        List<ClassEntity> listClass;
        if ("0".equals(grade)) {
            listClass = classRepository.findByIsDeletedFalse();
        } else {
            listClass = classRepository.findByGradeAndIsDeletedFalse(new StringBuilder("Khối ").append(grade).toString());
        }
        return new ServerResponseDto(ResponseCase.SUCCESS, listClass);
    }

    public List<ClassEntity> getListByGrade(String grade) {
        return classRepository.findByGradeAndIsDeletedFalse(grade);
    }

    public Map<Long, String> getMapClassNameByClassId(List<Long> listClassId) {
        List<ClassEntity> listClassEntity = classRepository.findByIdInAndIsDeletedFalse(listClassId);
        return listClassEntity
                .stream()
                .collect(Collectors.toMap(ClassEntity::getId, ClassEntity::getName));
    }

    public Map<Long, ClassEntity> getMapClassById(List<Long> listClassId) {
        List<ClassEntity> listClassEntity = classRepository.findByIdInAndIsDeletedFalse(listClassId);
        return listClassEntity
                .stream()
                .collect(Collectors.toMap(ClassEntity::getId, Function.identity()));
    }

    public List<Long> getALlClassId() {
        List<ClassEntity> listClassEntity = classRepository.findByIsDeletedFalse();
        return listClassEntity
                .stream()
                .map(ClassEntity::getId)
                .collect(Collectors.toList());
    }
}
