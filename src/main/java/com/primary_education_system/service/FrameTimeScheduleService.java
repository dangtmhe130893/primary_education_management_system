package com.primary_education_system.service;

import com.primary_education_system.entity.FrameTimeScheduleEntity;
import com.primary_education_system.repository.FrameTimeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameTimeScheduleService {
    @Autowired
    private FrameTimeScheduleRepository frameTimeScheduleRepository;

    public List<FrameTimeScheduleEntity> findAll() {
        return frameTimeScheduleRepository.findByIsDeletedFalse();
    }

    public String getNameFrameTimeById(Long frameTimeId) {
        FrameTimeScheduleEntity frameTimeScheduleEntity = frameTimeScheduleRepository.findByIdAndIsDeletedFalse(frameTimeId);
        return frameTimeScheduleEntity.getName();
    }
}
