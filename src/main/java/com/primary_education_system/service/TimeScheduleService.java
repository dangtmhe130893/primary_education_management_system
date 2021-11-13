package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.entity.TimeScheduleEntity;
import com.primary_education_system.enum_type.DayOfWeek;
import com.primary_education_system.repository.TimeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TimeScheduleService {
    @Autowired
    private TimeScheduleRepository timeScheduleRepository;

    @Autowired
    private FrameTimeScheduleService frameTimeScheduleService;

    public ServerResponseDto getTimeSchedule(Long classId) {
        List<TimeScheduleEntity> listTimeSchedule = timeScheduleRepository.findByClassIdAndIsDeletedFalse(classId);
        listTimeSchedule.forEach(timeScheduleEntity -> {
            if (timeScheduleEntity.getSubject() == null) {
                timeScheduleEntity.setSubject("------");
            }
        });
        return new ServerResponseDto(ResponseCase.SUCCESS, listTimeSchedule);
    }

    public void createTimeSchedule(Long classId) {
        List<FrameTimeScheduleEntity> listFrameTime = frameTimeScheduleService.findAll();
        List<TimeScheduleEntity> listTimeSchedule = new ArrayList<>();

        for (FrameTimeScheduleEntity frameTime : listFrameTime) {
            Long frameTimeId = frameTime.getId();
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                TimeScheduleEntity timeSchedule = new TimeScheduleEntity(dayOfWeek, frameTimeId, null, classId);
                timeSchedule.setCreatedTime(new Date());
                timeSchedule.setUpdatedTime(new Date());
                listTimeSchedule.add(timeSchedule);
            }
        }

        timeScheduleRepository.save(listTimeSchedule);
    }
}
