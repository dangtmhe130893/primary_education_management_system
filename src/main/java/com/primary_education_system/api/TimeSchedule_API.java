package com.primary_education_system.api;

import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.service.TimeScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timeSchedule")
public class TimeSchedule_API {

    @Autowired
    private TimeScheduleService timeScheduleService;

    @GetMapping("/getTimeSchedule/{classId}")
    public ResponseEntity<ServerResponseDto> getTimeSchedule(@PathVariable Long classId){
        return ResponseEntity.ok(timeScheduleService.getTimeSchedule(classId));
    }
}
