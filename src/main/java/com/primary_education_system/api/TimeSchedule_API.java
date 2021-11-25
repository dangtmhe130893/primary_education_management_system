package com.primary_education_system.api;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.time_schedule.InfoTimeScheduleTeacherDto;
import com.primary_education_system.dto.time_schedule.TimeScheduleRequestDto;
import com.primary_education_system.service.TimeScheduleService;
import com.primary_education_system.util.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timeSchedule")
public class TimeSchedule_API {

    @Autowired
    private TimeScheduleService timeScheduleService;


    @GetMapping("/getTimeSchedule/{classId}")
    public ResponseEntity<ServerResponseDto> getTimeSchedule(@PathVariable Long classId) {
        return ResponseEntity.ok(timeScheduleService.getTimeSchedule(classId));
    }

    @GetMapping("/getTimeScheduleForTeacher/")
    public ResponseEntity<ServerResponseDto> getTimeScheduleForTeacher(@AuthenticationPrincipal CustomUserDetails currentUser) {
        if (currentUser == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(timeScheduleService.getTimeScheduleForTeacher(currentUser.getUserId()));
    }

    @PostMapping("/save")
    public ResponseEntity<ServerResponseDto> save(@RequestBody TimeScheduleRequestDto timeScheduleRequestDto) {
        return ResponseEntity.ok(timeScheduleService.save(timeScheduleRequestDto));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServerResponseDto> detail(@PathVariable Long id) {
        return ResponseEntity.ok(timeScheduleService.detail(id));
    }

    @GetMapping("/getPageInfoTimeScheduleTeacher")
    public Page<InfoTimeScheduleTeacherDto> getInfoTimeScheduleTeacher(@RequestParam int size, @RequestParam int page,
                                                                       @RequestParam String sortDir, @RequestParam String sortField,
                                                                       @AuthenticationPrincipal CustomUserDetails currentUser) {
        Pageable pageable = PageableUtils.from(page, size, sortDir, sortField);
        return timeScheduleService.getInfoTimeScheduleTeacher(currentUser.getUserId(), pageable);
    }
}
