package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.time_schedule.TimeScheduleRequestDto;
import com.primary_education_system.entity.ClassEntity;
import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.entity.TimeScheduleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.enum_type.DayOfWeek;
import com.primary_education_system.repository.TimeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeScheduleService {
    @Autowired
    private TimeScheduleRepository timeScheduleRepository;

    @Autowired
    private FrameTimeScheduleService frameTimeScheduleService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    public ServerResponseDto getTimeSchedule(Long classId) {
        List<TimeScheduleEntity> listTimeSchedule = timeScheduleRepository.findByClassIdAndIsDeletedFalse(classId);

        List<Long> listSubjectId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getSubjectId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Map<Long, String> mapNameSubjectById = subjectService.getMapNameSubjectById(listSubjectId);

        List<Long> listTeacherId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getTeacherId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Map<Long, String> mapNameTeacherById = userService.getMapNameTeacherById(listTeacherId);

        listTimeSchedule.forEach(timeScheduleEntity -> {
            timeScheduleEntity.setNameSubject(mapNameSubjectById.getOrDefault(timeScheduleEntity.getSubjectId(), ""));
            timeScheduleEntity.setNameTeacher(mapNameTeacherById.getOrDefault(timeScheduleEntity.getTeacherId(), ""));
        });
        return new ServerResponseDto(ResponseCase.SUCCESS, listTimeSchedule);
    }

    public ServerResponseDto getTimeScheduleForTeacher(Long teacherId) {
        List<TimeScheduleEntity> listTimeSchedule = timeScheduleRepository.findByTeacherIdAndIsDeletedFalse(teacherId);

        List<Long> listClassId = listTimeSchedule
                .stream()
                .map(TimeScheduleEntity::getClassId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Map<Long, ClassEntity> mapClassById = classService.getMapClassById(listClassId);

        listTimeSchedule.forEach(timeScheduleEntity -> {
            timeScheduleEntity.setClassName(mapClassById.get(timeScheduleEntity.getClassId()).getName());
            timeScheduleEntity.setClassRoom(mapClassById.get(timeScheduleEntity.getClassId()).getRoom());
        });
        return new ServerResponseDto(ResponseCase.SUCCESS, listTimeSchedule);
    }

    public void createTimeSchedule(Long classId) {
        List<FrameTimeScheduleEntity> listFrameTime = frameTimeScheduleService.findAll();
        List<TimeScheduleEntity> listTimeSchedule = new ArrayList<>();

        for (FrameTimeScheduleEntity frameTime : listFrameTime) {
            Long frameTimeId = frameTime.getId();
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                TimeScheduleEntity timeSchedule = new TimeScheduleEntity(dayOfWeek, frameTimeId, null, null, classId);
                timeSchedule.setCreatedTime(new Date());
                timeSchedule.setUpdatedTime(new Date());
                listTimeSchedule.add(timeSchedule);
            }
        }

        timeScheduleRepository.save(listTimeSchedule);
    }

    public ServerResponseDto save(TimeScheduleRequestDto timeScheduleRequestDto) {
        TimeScheduleEntity timeScheduleEntity = timeScheduleRepository
                .findByIdAndIsDeletedFalse(timeScheduleRequestDto.getTimeScheduleId());


        /*
         * trùng lịch nếu: + khác classId
         *                 + trùng dayOfWeek
         *                 + trùng frameTimeId
         *                 + trùng teacherId
         * */
        Long classId = timeScheduleEntity.getClassId();
        DayOfWeek dayOfWeek = timeScheduleEntity.getDayOfWeek();
        Long frameTimeId = timeScheduleEntity.getFrameTimeId();
        Long teacherId = timeScheduleRequestDto.getTeacherId();

        if (checkSameTimeSchedule(classId, dayOfWeek, frameTimeId, teacherId)) {
            UserEntity teacherDuplicate = userService.findByIdAndIsDeletedFalse(teacherId);
            return new ServerResponseDto(ResponseCase.SAME_TIME_SCHEDULE, teacherDuplicate.getName());
        }

        if (timeScheduleEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        timeScheduleEntity.setSubjectId(timeScheduleRequestDto.getSubjectId());
        timeScheduleEntity.setTeacherId(timeScheduleRequestDto.getTeacherId());

        timeScheduleRepository.save(timeScheduleEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    private boolean checkSameTimeSchedule(Long classId, DayOfWeek dayOfWeek, Long frameTimeId, Long teacherId) {
        int numberSameTimeSchedule = (int) timeScheduleRepository.countSameTimeSchedule(classId, dayOfWeek, frameTimeId, teacherId);
        return numberSameTimeSchedule > 0;
    }

    public ServerResponseDto detail(Long timeScheduleId) {
        TimeScheduleEntity timeScheduleEntity = timeScheduleRepository.findByIdAndIsDeletedFalse(timeScheduleId);
        if (timeScheduleEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }

        String nameFrameTime = frameTimeScheduleService.getNameFrameTimeById(timeScheduleEntity.getFrameTimeId());
        timeScheduleEntity.setNameFrameTime(nameFrameTime);

        Long subjectId = timeScheduleEntity.getSubjectId();
        List<UserEntity> listTeacher = userService.getListTeacherBySubjectId(subjectId);
        timeScheduleEntity.setListTeacher(listTeacher);
        return new ServerResponseDto(ResponseCase.SUCCESS, timeScheduleEntity);
    }

    public void deleteTimeScheduleByClassId(Long classId) {
        List<TimeScheduleEntity> listTimeSchedule = timeScheduleRepository.findByClassIdAndIsDeletedFalse(classId);
        listTimeSchedule.forEach(timeSchedule -> {
            timeSchedule.setDeleted(true);
        });
        timeScheduleRepository.save(listTimeSchedule);
    }
}
